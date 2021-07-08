package Level3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.net.URI;

@RestController
@RequestMapping(path = "/cardDistribution")
public class RestControllerForCard {
    // Create a POST method
    // to add an employee
    // to the list
    @GetMapping("/cardDistribution")
    public String index() {
        return "test";
    }

    @PostMapping(
            path = "/Level3.GiftCard/{company}{user}{amount}{walletType}{inputFile}",
            consumes = "application/json",
            produces = "application/json")

    public ResponseEntity<Object> addGiftCard(@PathVariable("company") String company, @PathVariable("user") Long user, @PathVariable("amount") Integer amount, @PathVariable("walletType") Long walletType, @PathVariable("inputFile") File inputFile,
            @RequestBody GiftCard giftCard)
    {
        new GiftCard(company, user, amount, walletType, inputFile);
        URI location
                = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{company}")
                .buildAndExpand()
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @PostMapping(
            path = "/Level3.MealVoucher",
            consumes = "application/json",
            produces = "application/json")

    public ResponseEntity<Object> addMealVoucher(@PathVariable("company") String company, @PathVariable("user") Long user, @PathVariable("amount") Integer amount, @PathVariable("walletType") Long walletType, @PathVariable("inputFile") File inputFile,
                                              @RequestBody GiftCard MealVoucher)
    {
        // Creating an ID of an employee
        // from the number of employees

        new MealVoucher(company, user, amount, walletType, inputFile);

        URI location
                = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{company}")
                .buildAndExpand()
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }
}
