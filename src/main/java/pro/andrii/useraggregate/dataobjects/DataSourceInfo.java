package pro.andrii.useraggregate.dataobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceInfo {
    private String name;

    private String strategy;
    private String url;
    private String user;
    private String password;

    private String table;
    private Map<String, String> mapping;
}
