package com.allane.leasingcontracts.service;

import com.allane.leasingcontracts.model.Vehicle;
import com.allane.leasingcontracts.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest
{
    @InjectMocks
    private VehicleService service;

    @Mock
    private VehicleRepository repository;

    @Test
    void shouldSaveAVehicle()
    {
        final Vehicle vehicleToSave = new Vehicle("Mercedes", "A180", 2015, "", 32000);
        when(repository.save(any(Vehicle.class))).thenReturn(vehicleToSave);

        final Vehicle actual = service.saveVehicle(new Vehicle());

        assertThat(actual).usingRecursiveComparison().isEqualTo(vehicleToSave);
        verify(repository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void shouldNotSaveVehicle_whenVehicleWithSameVINExists()
    {
        final Vehicle vehicleToSave = new Vehicle("Mercedes", "A180", 2015, "123", 32000);
        when(repository.findVehicleByVin(any(String.class))).thenReturn(Optional.of(vehicleToSave));

        assertThatIllegalArgumentException().isThrownBy(() -> service.saveVehicle(vehicleToSave));
    }
}
