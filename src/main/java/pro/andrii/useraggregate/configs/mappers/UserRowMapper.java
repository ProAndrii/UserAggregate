package pro.andrii.useraggregate.configs.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class UserRowMapper implements RowMapper<Map<String, String>> {
    private final Map<String, String> fields;

    @Override
    public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, String> res = new HashMap<>();

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            res.put(entry.getKey(), rs.getString(entry.getValue()));
        }

        return res;
    }
}
