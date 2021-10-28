package net.binarypaper.demo.resourceserver.employee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Data
@Schema(description = "An employee working for the organisation")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @JsonView({
        JsonViews.List.class, 
        JsonViews.View.class, 
        JsonViews.ViewAll.class, 
        JsonViews.Add.class,
        JsonViews.Update.class
    })
    @Schema(description = "The unique ID of the employee", example = "123")
    private Long id;

    @NotNull
    @JsonView({
        JsonViews.List.class, 
        JsonViews.View.class, 
        JsonViews.ViewAll.class, 
        JsonViews.Add.class,
        JsonViews.Update.class
    })
    @Schema(description = "The first name of the employee", example = "John")
    private String firstName;

    @NotNull
    @JsonView({
        JsonViews.List.class, 
        JsonViews.View.class, 
        JsonViews.ViewAll.class, 
        JsonViews.Add.class,
        JsonViews.Update.class
    })
    @Schema(description = "The last name of the employee", example = "Smith")
    private String lastName;

    @JsonView({
        JsonViews.View.class, 
        JsonViews.ViewAll.class, 
        JsonViews.Add.class,
        JsonViews.Update.class
    })
    @Schema(description = "The phone number of the employee", example = "+31 68 123 1234")
    private String phoneNumber;

    @Email
    @JsonView({
        JsonViews.View.class, 
        JsonViews.ViewAll.class, 
        JsonViews.Add.class,
        JsonViews.Update.class
    })
    @Schema(description = "The email address of the employee", example = "john.smith@example.com")
    private String email;

    @NotNull
    @JsonView({
        JsonViews.List.class, 
        JsonViews.View.class, 
        JsonViews.ViewAll.class, 
        JsonViews.Add.class,
        JsonViews.Update.class
    })
    @Schema(description = "The department of the employee", example = "Sales")
    private String department;

    @JsonView({
        JsonViews.ViewAll.class, 
        JsonViews.Add.class,
        JsonViews.Update.class
    })
    @Schema(description = "The name of the manager of the employee", example = "Jane Jackson")
    private String manager;

    @JsonView({
        JsonViews.ViewAll.class, 
        JsonViews.Add.class,
        JsonViews.Update.class
    })
    @Schema(description = "A note about the employee", example = "Just an example employee")
    private String note;

    public interface JsonViews {

        public interface List {
        }

        public interface View {
        }

        public interface ViewAll {
        }

        public interface Add {
        }

        public interface Update {
        }
    }
}