package com.allane.leasingcontracts.service;

import com.allane.leasingcontracts.config.LeasingContractsConfig;
import com.allane.leasingcontracts.dto.VehicleDTO;
import com.allane.leasingcontracts.model.Vehicle;
import com.allane.leasingcontracts.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = LeasingContractsConfig.class)
class VehicleServiceTest
{
    @Autowired
    @Spy
    private ModelMapper mapper;

    @Mock
    private VehicleRepository repository;

    @InjectMocks
    private VehicleService service;


    @Test
    void shouldReturnTwoVehicles()
    {
        List<Vehicle> expectedVehicles = List.of(
            new Vehicle("Audi", "A4", 2012, "987", BigDecimal.valueOf(41000)),
            new Vehicle("Mercedes", "A180", 2015, "123", BigDecimal.valueOf(32000))
        );
        when(repository.findAll()).thenReturn(expectedVehicles);

        List<VehicleDTO> actualVehicles = service.getVehicles();

        assertThat(actualVehicles).usingRecursiveComparison().isEqualTo(expectedVehicles);
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);

    }

    @Test
    void shouldSaveAVehicle()
    {
        final Vehicle vehicleToSave = new Vehicle("Mercedes", "A180", 2015, "", BigDecimal.valueOf(32000));
        when(repository.save(any(Vehicle.class))).thenReturn(vehicleToSave);

        final Vehicle actual = service.saveVehicle(new VehicleDTO());

        assertThat(actual).usingRecursiveComparison().isEqualTo(vehicleToSave);
        verify(repository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void shouldNotSaveVehicle_whenVehicleWithSameVINExists()
    {
        final Vehicle vehicleToSave = new Vehicle("Mercedes", "A180", 2015, "123", BigDecimal.valueOf(32000));
        final VehicleDTO dto = mapper.map(vehicleToSave, VehicleDTO.class);
        when(repository.findVehicleByVin(any(String.class))).thenReturn(Optional.of(vehicleToSave));

        assertThatIllegalArgumentException().isThrownBy(() -> service.saveVehicle(dto));
    }

    @Test
    void shouldFailWhenDeletingNonExistentVehicle()
    {
        when(repository.existsById(any(Long.class))).thenReturn(false);
        assertThatIllegalArgumentException().isThrownBy(() -> service.deleteVehicle(999L));
    }

    @Test
    void shouldUpdateVehicle()
    {
        Vehicle vehicleToUpdate = new Vehicle("Mercedes", "A180", 2015, "123", BigDecimal.valueOf(32000));
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(vehicleToUpdate));

        VehicleDTO dto = new VehicleDTO("Mercedes", "A220", 2015, "123", BigDecimal.valueOf(32000));
        VehicleDTO updatedDto = service.updateVehicle(1L, dto);

        assertThat(updatedDto.getModel()).isEqualTo(dto.getModel());
        verify(repository, times(1)).findById(any(Long.class));
    }

    @Test
    void shouldFailWhenUpdatingWithAlreadyExistingVIN()
    {
        Vehicle vehicleToUpdate = new Vehicle("Mercedes", "A180", 2015, "", BigDecimal.valueOf(32000));
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(vehicleToUpdate));
        when(repository.existsByIdIsNotAndVinEquals(any(Long.class), any(String.class))).thenReturn(true);

        VehicleDTO dto = new VehicleDTO("Mercedes", "A180", 2015, "42", BigDecimal.valueOf(32000));
        assertThatIllegalArgumentException()
            .isThrownBy(() -> service.updateVehicle(1L, dto))
            .withMessageContaining("VIN")
            .withMessageContaining("already");


    }
}