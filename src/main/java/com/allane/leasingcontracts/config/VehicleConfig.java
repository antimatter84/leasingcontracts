package com.allane.leasingcontracts.config;

import com.allane.leasingcontracts.model.Vehicle;
import com.allane.leasingcontracts.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class VehicleConfig
{
    @Bean
    CommandLineRunner commandLineRunner(VehicleRepository repository)
    {
        return args -> {
            Vehicle i30 = new Vehicle("Hyundai", "i30", 2013, "", 12300);
            Vehicle corsa = new Vehicle("Opel", "Corsa", 2011, 6800);

            repository.saveAll(List.of(i30, corsa));
        };
    }
}
