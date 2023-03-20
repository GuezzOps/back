package com.esprit.examen.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.esprit.examen.entities.Facture;
import com.esprit.examen.repositories.FactureRepository;

import lombok.extern.slf4j.Slf4j;

@TestMethodOrder(OrderAnnotation.class)
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class FactureServiceImplTest {

	FactureRepository factureRepository=Mockito.mock(FactureRepository.class);

	@InjectMocks
	FactureServiceImpl factureService;
	
	
	@Test
	public void TestRetrieveFacture() {

	Facture facture = new Facture(null, 12, 300, null, null, true, null, null, null);
	facture.setIdFacture(1L);

	Mockito.when(factureRepository.findById(1L)).thenReturn(Optional.of(facture));
	factureService.retrieveFacture(1L);
	Assertions.assertNotNull(facture);

	log.info(" TestRetrieveFacture - TEST SUCCESS");

	}
	
	
	
	
	@Test
	public void TestRetrieveAllFactures()
	{
	List<Facture> factures = new ArrayList<Facture>() {

	{
	add(new Facture(null, 2356, 50000, null, null, false, null, null, null));
	add(new Facture(null, 3000, 60000, null, null, true, null, null, null));
	add(new Facture(null, 4000, 70000, null, null, true, null, null, null));
	}};

	
	

	when(factureService.retrieveAllFactures()).thenReturn(factures);
	//test
	List<Facture> facturesList = factureService.retrieveAllFactures();
	assertEquals(3,facturesList.size());
	log.info("NB FACTURES : "+String.valueOf(factures.size()));
	
	log.info(" TestRetrieveAllFactures - TEST SUCCESS");


	}
	
	
	@Test
	public void TestAddFacture()
	{

		Facture facture = new Facture(null, 120, 3000, null, null, false, null, null, null);
		facture.setIdFacture(2L);

		factureService.addFacture(facture);
	verify(factureRepository, times(1)).save(facture);
	
	log.info(" TestAddFacture - TEST SUCCESS");
	}
	
	
	@Test
	public void TestCancelFacture(){

	Facture facture = new Facture(null, 4000, 70000, null, null, true, null, null, null);
	facture.setIdFacture(7L);



	Mockito.lenient().when(factureRepository.findById(facture.getIdFacture())).thenReturn(Optional.of(facture));

	factureService.cancelFacture(7L);
	verify(factureRepository).deleteById(facture.getIdFacture());

	log.info("  TestDeleteFacture - TEST SUCCESS ");
	}
	
	
}
