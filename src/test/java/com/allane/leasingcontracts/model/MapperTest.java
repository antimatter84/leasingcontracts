package com.allane.leasingcontracts.model;

import com.allane.leasingcontracts.config.LeasingContractsConfig;
import com.allane.leasingcontracts.dto.CustomerDTO;
import com.allane.leasingcontracts.dto.LeasingContractDTO;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = LeasingContractsConfig.class)
class MapperTest
{
    @Autowired
    private ModelMapper mapper;

    @Test
    void thatCustomerIsConvertedToDTO()
    {
        Customer wick = new Customer("John", "Wick", LocalDate.of(1972, 4, 1));
        CustomerDTO wickDto = mapper.map(wick, CustomerDTO.class);

        assertThat(wick.getFirstName()).isEqualTo(wickDto.getFirstName());
        assertThat(wick.getLastName()).isEqualTo(wickDto.getLastName());
        assertThat(wick.getDateOfBirth()).isEqualTo(wickDto.getDateOfBirth());
    }

    @Test
    void thatDtoIsConvertedToCustomer()
    {
        CustomerDTO wickDto = new CustomerDTO("John", "Wick", LocalDate.of(1972, 4, 1));
        Customer wick = mapper.map(wickDto, Customer.class);

        assertThat(wick.getFirstName()).isEqualTo(wickDto.getFirstName());
        assertThat(wick.getLastName()).isEqualTo(wickDto.getLastName());
        assertThat(wick.getDateOfBirth()).isEqualTo(wickDto.getDateOfBirth());
    }

    @Test
    void thatLeasingContractIsConvertedToOverviewDTO()
    {
        LeasingContract contract = new LeasingContract();
        contract.setId(999L);
        contract.setContractNumber("1234");
        contract.setLeasingRate(BigDecimal.valueOf(99.9));
        contract.setVehicle(new Vehicle(
            "Ford", "Focus", 2018, "VIN123", BigDecimal.valueOf(9000.0)));
        contract.setCustomer(new Customer(
            "John", "Wick", LocalDate.of(1972, 4, 1)));

        LeasingContractDTO overviewDTO = mapper.map(contract, LeasingContractDTO.class);

        assertThat(overviewDTO.getId()).isEqualTo(contract.getId());
        assertThat(overviewDTO.getContractNumber()).isEqualTo(contract.getContractNumber());
        assertThat(overviewDTO.getRate()).isCloseTo(contract.getLeasingRate(), Percentage.withPercentage(1));
        assertThat(overviewDTO.getVin()).isEqualTo(contract.getVehicle().getVin());
        assertThat(overviewDTO.getPrice()).isCloseTo(contract.getVehicle().getPrice(), Percentage.withPercentage(1));

        assertThat(overviewDTO.getFullName()).isEqualTo(
                contract.getCustomer().getFirstName() + " " + contract.getCustomer().getLastName());
        assertThat(overviewDTO.getVehicleDescription()).isEqualTo(
                String.format("%s %s (%d)",
                        contract.getVehicle().getBrand(),
                        contract.getVehicle().getModel(),
                        contract.getVehicle().getModelYear()));
    }
}
