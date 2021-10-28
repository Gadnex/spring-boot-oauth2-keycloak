package net.binarypaper.demo.resourceserver.employee;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "employees", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "security_auth")
@Tag(name = "Employee Controller", description = "API to retrieve employee information")
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    @JsonView(Employee.JsonViews.List.class)
    @RolesAllowed("USER")
    @Operation(
        summary = "Get a list employees",
        description = "Get a list of all employees. User role USER required to access this method."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "List of employees returned."
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized. User not logged in.",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. User logged in, but does not have the correct role to access this endpoint.",
            content = @Content
        )
    })
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll(Sort.by("firstName", "lastName"));
    }

    @GetMapping(path = "{employeeId}")
    @JsonView(Employee.JsonViews.View.class)
    @RolesAllowed("USER")
    @Operation(
        summary = "Get employee details by ID",
        description = "Get a limited set of employee details by employee ID. User role USER required to access this method."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Employee details returned."
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized. User not logged in.",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. User logged in, but does not have the correct role to access this endpoint.",
            content = @Content
        )
    })
    public Optional<Employee> getEmployee(
        @PathVariable
        @Parameter(
            description = "The unique ID of the employee",
            example = "1"
        )
        Long employeeId
    ) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The employeeId is invalid");
        }
        return employee;
    }

    @GetMapping(path = "{employeeId}/all")
    @JsonView(Employee.JsonViews.ViewAll.class)
    @RolesAllowed("ADMIN")
    @Operation(
        summary = "Get ALL employee details by ID",
        description = "Get all employee details by employee ID. User role ADMIN required to access this method."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Employee details returned."
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized. User not logged in.",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. User logged in, but does not have the correct role to access this endpoint.",
            content = @Content
        )
    })
    public Optional<Employee> getEmployeeAllDetails(
        @PathVariable
        @Parameter(
            description = "The unique ID of the employee",
            example = "1"
        )
        Long employeeId
    ) {
        return getEmployee(employeeId);
    }

}