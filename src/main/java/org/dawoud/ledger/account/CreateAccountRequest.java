package org.dawoud.ledger.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(

        @NotBlank
        @Size(max =100)
        String name ,

        @NotBlank
        @Pattern(regexp = "^[A-Za-z]{3}$")
        String currency
) {
}
