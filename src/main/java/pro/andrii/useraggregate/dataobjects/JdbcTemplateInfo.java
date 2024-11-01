package pro.andrii.useraggregate.dataobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class JdbcTemplateInfo {
    private String dataSourceName;

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Map<String, String>> userRowMapper;

    private String table;
    private Map<String, String> fieldMapping;
}
