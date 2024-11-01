package pro.andrii.useraggregate.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.andrii.useraggregate.services.UserAggregationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserAggregateController {
    private final UserAggregationService userAggregationService;

    @Operation(
            summary = "Get aggregated user data",
            description = "Fetches a list of aggregated user data as key-value pairs",
            tags = { "User Aggregation" }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of aggregated users",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = """
                                            [
                                              {
                                                "id": "example-user-id-1",
                                                "username": "user-1",
                                                "name": "User",
                                                "surname": "Userenko"
                                              },
                                              {
                                                "id": "example-user-id-2",
                                                "username": "user-2",
                                                "name": "Testuser",
                                                "surname": "Testov"
                                              }
                                            ]
                                            """)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping
    public List<Map<String, String>> getUsers() {
        return userAggregationService.aggregateUsers();
    }
}
