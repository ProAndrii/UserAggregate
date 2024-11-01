package pro.andrii.useraggregate.configs;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pro.andrii.useraggregate.configs.mappers.UserRowMapper;
import pro.andrii.useraggregate.dataobjects.DataSourceInfo;
import pro.andrii.useraggregate.dataobjects.JdbcTemplateInfo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class DataSourcesConfiguration {
    private List<DataSourceInfo> dataSources;

    @Bean
    public Map<String, JdbcTemplateInfo> jdbcTemplates() {
        validateDataSourceInfo(dataSources);

        Map<String, JdbcTemplateInfo> jdbcTemplateMap = new HashMap<>();

        for (DataSourceInfo config : dataSources) {
            DataSource dataSource = DataSourceBuilder.create()
                    .url(config.getUrl())
                    .username(config.getUser())
                    .password(config.getPassword())
                    .driverClassName(getDriverClassName(config.getStrategy()))
                    .build();

            JdbcTemplateInfo jdbcTemplateInfo = JdbcTemplateInfo
                    .builder()
                    .dataSourceName(config.getName())
                    .jdbcTemplate(new JdbcTemplate(dataSource))
                    .userRowMapper(new UserRowMapper(config.getMapping()))
                    .table(config.getTable())
                    .fieldMapping(config.getMapping())
                    .build();

            jdbcTemplateMap.put(config.getName(), jdbcTemplateInfo);
        }

        return jdbcTemplateMap;
    }

    private void validateDataSourceInfo(List<DataSourceInfo> dataSources) {
        if (Objects.isNull(dataSources)) {
            throw new IllegalArgumentException("dataSources must not be empty");
        }

        for (DataSourceInfo dataSource : dataSources) {
            if (Objects.isNull(dataSource.getName()) || dataSource.getName().isEmpty()) {
                throw new IllegalArgumentException("name must not be empty for datasource: " + dataSource.getName());
            }
            if (Objects.isNull(dataSource.getUrl()) || dataSource.getUrl().isEmpty()) {
                throw new IllegalArgumentException("url must not be empty for datasource: " + dataSource.getName());
            }
            if (Objects.isNull(dataSource.getTable()) || dataSource.getTable().isEmpty()) {
                throw new IllegalArgumentException("table must not be empty for datasource: " + dataSource.getName());
            }
            if (Objects.isNull(dataSource.getUser()) || dataSource.getUser().isEmpty()) {
                throw new IllegalArgumentException("user must not be empty for datasource: " + dataSource.getName());
            }
            if (Objects.isNull(dataSource.getPassword()) || dataSource.getPassword().isEmpty()) {
                throw new IllegalArgumentException("password must not be empty for datasource: " + dataSource.getName());
            }
            if (Objects.isNull(dataSource.getMapping())) {
                throw new IllegalArgumentException("mapping must not be empty for datasource: " + dataSource.getName());
            }
            if (dataSource.getMapping().values().stream().anyMatch(String::isEmpty)) {
                throw new IllegalArgumentException("mapping must not be empty for datasource: " + dataSource.getName());
            }
        }
    }


    // A small method example of a case when required to support more than one database type
    private String getDriverClassName(String strategy) {
        if (Objects.isNull(strategy)) {
            return "org.postgresql.Driver";
        }

        switch (strategy) {
            case "postgres":
                return "org.postgresql.Driver";
            default:
                throw new IllegalArgumentException("Unknown strategy: " + strategy);
        }
    }
}
