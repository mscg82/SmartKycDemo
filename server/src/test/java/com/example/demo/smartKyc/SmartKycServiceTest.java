package com.example.demo.smartKyc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SmartKycServiceTest
{
    @Mock
    private SmartKycRepository smartKycRepository;

    private SmartKycService smartKycService = new SmartKycService(smartKycRepository);

    @Test
    void testInvalidPieces1()
    {
    	WrapperSmartKycDTO wrapperSmartKycDTO = new WrapperSmartKycDTO();
        wrapperSmartKycDTO.setFirstValue(createSmartKycDTO(1, 15));
        wrapperSmartKycDTO.setSmartKycDTOS(Collections.singleton(createSmartKycDTO(15, 5)));

        Integer highestValue = smartKycService.getChainWithHighestValue(wrapperSmartKycDTO);
        // this should fail somewhere, either in building the pieces or in the service
        Assertions.assertThat(highestValue).isNotEqualTo(15);
    }

    @Test
    void testInvalidPieces2()
    {
    	WrapperSmartKycDTO wrapperSmartKycDTO = new WrapperSmartKycDTO();
    	wrapperSmartKycDTO.setFirstValue(createSmartKycDTO(1, 5));
    	Set<SmartKycDTO> pieces = new HashSet<>();
    	pieces.add(createSmartKycDTO(5, 5));
    	pieces.add(createSmartKycDTO(5, 4));
    	wrapperSmartKycDTO.setSmartKycDTOS(pieces);

    	Integer highestValue = smartKycService.getChainWithHighestValue(wrapperSmartKycDTO);
    	// this should fail somewhere, either in building the pieces or in the service
    	Assertions.assertThat(highestValue).isNotEqualTo(5);
    }

    @Test
    void testTwoEqualsPieces()
    {
    	WrapperSmartKycDTO wrapperSmartKycDTO = new WrapperSmartKycDTO();
        wrapperSmartKycDTO.setFirstValue(createSmartKycDTO(1, 3));
        wrapperSmartKycDTO.setSmartKycDTOS(Collections.singleton(createSmartKycDTO(1, 3)));

        Integer highestValue = smartKycService.getChainWithHighestValue(wrapperSmartKycDTO);
        Assertions.assertThat(highestValue).isEqualTo(3);
    }

    @Test
    void testSetWithTwoEqualsPieces()
    {
    	WrapperSmartKycDTO wrapperSmartKycDTO = new WrapperSmartKycDTO();
    	wrapperSmartKycDTO.setFirstValue(createSmartKycDTO(1, 3));
    	Set<SmartKycDTO> pieces = new HashSet<>();
    	pieces.add(createSmartKycDTO(3, 5));
    	pieces.add(createSmartKycDTO(5, 1));
    	pieces.add(createSmartKycDTO(1, 3));
    	wrapperSmartKycDTO.setSmartKycDTOS(pieces);

    	Integer highestValue = smartKycService.getChainWithHighestValue(wrapperSmartKycDTO);
    	Assertions.assertThat(highestValue).isEqualTo(9);
    }

    @Test
    void testSetWithPiecesAttachingOnBothSides()
    {
    	WrapperSmartKycDTO wrapperSmartKycDTO = new WrapperSmartKycDTO();
    	wrapperSmartKycDTO.setFirstValue(createSmartKycDTO(1, 3));
    	Set<SmartKycDTO> pieces = new HashSet<>();
    	pieces.add(createSmartKycDTO(3, 5));
    	pieces.add(createSmartKycDTO(4, 1));
    	wrapperSmartKycDTO.setSmartKycDTOS(pieces);

    	Integer highestValue = smartKycService.getChainWithHighestValue(wrapperSmartKycDTO);
    	Assertions.assertThat(highestValue).isEqualTo(4);
    }

    @Test
    void testCaseScenarioOne()
    {
        WrapperSmartKycDTO wrapperSmartKycDTO = new WrapperSmartKycDTO();
        wrapperSmartKycDTO.setFirstValue(createSmartKycDTO(1, 3));
        wrapperSmartKycDTO.setSmartKycDTOS(fillOutTestDataSetOne());

        Integer highestValue = smartKycService.getChainWithHighestValue(wrapperSmartKycDTO);
        Assertions.assertThat(highestValue).isEqualTo(44);
    }

    @Test
    void testCaseScenarioTwo()
    {
        WrapperSmartKycDTO wrapperSmartKycDTO = new WrapperSmartKycDTO();
        wrapperSmartKycDTO.setFirstValue(createSmartKycDTO(8, 9));
        wrapperSmartKycDTO.setSmartKycDTOS(fillOutTestDataSetOne());

        Integer highestValue = smartKycService.getChainWithHighestValue(wrapperSmartKycDTO);
        Assertions.assertThat(highestValue).isEqualTo(42);
    }

    @Test
    void testCaseScenarioThree()
    {
        WrapperSmartKycDTO wrapperSmartKycDTO = new WrapperSmartKycDTO();
        wrapperSmartKycDTO.setFirstValue(createSmartKycDTO(1, 4));
        wrapperSmartKycDTO.setSmartKycDTOS(fillOutTestDataSetTwo());

        Integer highestValue = smartKycService.getChainWithHighestValue(wrapperSmartKycDTO);
        Assertions.assertThat(highestValue).isEqualTo(20);
    }

    @Test
    void testCaseScenarioFour()
    {
        WrapperSmartKycDTO wrapperSmartKycDTO = new WrapperSmartKycDTO();
        wrapperSmartKycDTO.setFirstValue(createSmartKycDTO(1, 4));
        wrapperSmartKycDTO.setSmartKycDTOS(fillOutTestDataSetThree());

        Integer highestValue = smartKycService.getChainWithHighestValue(wrapperSmartKycDTO);
        Assertions.assertThat(highestValue).isEqualTo(26);
    }

    @Test
    void testDublicate()
    {
        WrapperSmartKycDTO wrapperSmartKycDTO = new WrapperSmartKycDTO();
        wrapperSmartKycDTO.setFirstValue(createSmartKycDTO(8, 9));
        wrapperSmartKycDTO.setSmartKycDTOS(fillOutTestDataSetFour());

        Integer highestValue = smartKycService.getChainWithHighestValue(wrapperSmartKycDTO);
        Assertions.assertThat(highestValue).isEqualTo(12);
    }

    private Set<SmartKycDTO> fillOutTestDataSetTwo()
    {
        Set<SmartKycDTO> smartKycDTOS = new HashSet<>();
        addToSet(smartKycDTOS, 4, 8);
        addToSet(smartKycDTOS, 1,4);
        addToSet(smartKycDTOS, 5,4);
        addToSet(smartKycDTOS, 8,5);
        addToSet(smartKycDTOS, 2,1);
        addToSet(smartKycDTOS, 5,2);
        return smartKycDTOS;
    }

    private Set<SmartKycDTO> fillOutTestDataSetThree()
    {
        Set<SmartKycDTO> smartKycDTOS = new HashSet<>();
        addToSet(smartKycDTOS, 4, 8);
        addToSet(smartKycDTOS, 9, 8);
        addToSet(smartKycDTOS, 5,4);
        addToSet(smartKycDTOS, 9,5);
        addToSet(smartKycDTOS, 5,2);
        return smartKycDTOS;
    }

    private Set<SmartKycDTO> fillOutTestDataSetOne()
    {
        Set<SmartKycDTO> smartKycDTOS = new HashSet<>();
        addToSet(smartKycDTOS, 2, 3);
        addToSet(smartKycDTOS, 4,2);
        addToSet(smartKycDTOS, 5,8);
        addToSet(smartKycDTOS, 1,3);
        addToSet(smartKycDTOS, 4,8);
        addToSet(smartKycDTOS, 7,1);
        addToSet(smartKycDTOS, 6,1);
        addToSet(smartKycDTOS, 2,4);
        addToSet(smartKycDTOS, 5,4);
        addToSet(smartKycDTOS, 8,9);
        addToSet(smartKycDTOS, 3,6);
        addToSet(smartKycDTOS, 9,1);
        return smartKycDTOS;
    }

    private Set<SmartKycDTO> fillOutTestDataSetFour()
    {
        Set<SmartKycDTO> smartKycDTOS = new HashSet<>();
        addToSet(smartKycDTOS, 4, 8);
        addToSet(smartKycDTOS, 1,4);
        addToSet(smartKycDTOS, 1,4);
        addToSet(smartKycDTOS, 8,5);
        addToSet(smartKycDTOS, 1,1);
        addToSet(smartKycDTOS, 8,5);
        addToSet(smartKycDTOS, 6,6);
        return smartKycDTOS;
    }

    private void addToSet(Set<SmartKycDTO> smartKycDTOS, Integer leftValue, Integer rightValue)
    {
        smartKycDTOS.add(createSmartKycDTO(leftValue, rightValue));
    }

    private SmartKycDTO createSmartKycDTO(Integer leftValue, Integer rightValue)
    {
        return new SmartKycDTO(leftValue, rightValue);
    }

}
