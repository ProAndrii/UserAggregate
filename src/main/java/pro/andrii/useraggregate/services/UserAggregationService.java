package pro.andrii.useraggregate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import pro.andrii.useraggregate.dataobjects.JdbcTemplateInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserAggregationService {

    private final Map<String, JdbcTemplateInfo> jdbcTemplates;

    public List<Map<String, String>> aggregateUsers() {
        List<Map<String, String>> results = new ArrayList<>();

        for (JdbcTemplateInfo info : jdbcTemplates.values()) {
            JdbcTemplate jdbcTemplate = info.getJdbcTemplate();
            RowMapper<Map<String, String>> userRowMapper = info.getUserRowMapper();
            String sql = toSqlSelectAll(info.getFieldMapping(), info.getTable());

            results.addAll(jdbcTemplate.query(sql, userRowMapper));
        }

        return results;
    }

    private String toSqlSelectAll(Map<String, String> fields, String tableName) {
        return String.format(
                "select %s from %s;",
                String.join(", ", fields.values()),
                tableName
        );
    }
}
