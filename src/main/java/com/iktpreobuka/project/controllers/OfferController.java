package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.OfferEntity.OfferStatus;

@RestController
@RequestMapping(path = "/project/offers")
public class OfferController {

	List<OfferEntity> offers = new ArrayList<>();

	/*
	 * 3.2 u paketu com.iktpreobuka.project.controllers napraviti klasu
	 * OfferController sa metodom get DB() koja vraća listu svih ponuda
	 */
	private List<OfferEntity> getDB() {
		if (offers.size() == 0) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, 5);
			OfferEntity o1 = new OfferEntity(1, "2 tickets to Killers concert", "Enjoz!!!", new Date(), cal.getTime(),
					100000.00, 6500.00, " ", 10, 0, OfferStatus.WAIT_FOR_APPROVING);
			OfferEntity o2 = new OfferEntity(2, "VIVAX 24LE76T2", "Don't miss this fantastic offer!", new Date(),
					cal.getTime(), 200000.00, 16500.00, " ", 5, 0, OfferStatus.WAIT_FOR_APPROVING);
			OfferEntity o3 = new OfferEntity(3, "Dinner for two in Aqua Doria", "Excellent offer!", new Date(),
					cal.getTime(), 6000.00, 3500.00, " ", 4, 0, OfferStatus.WAIT_FOR_APPROVING);

			offers.add(o1);
			offers.add(o2);
			offers.add(o3);
		}
		return offers;
	}

	/*
	 * 3.3 kreirati REST endpoint koja vraća listu svih ponuda • putanja
	 * /project/offers
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public List<OfferEntity> getAllOffers() {
		return getDB();
	}

	/*
	 * 3.4 kreirati REST endpoint koji omogućava dodavanje nove ponude • putanja
	 * /project/offers • metoda treba da vrati dodatu ponudu
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public OfferEntity createNewOffer(@RequestBody OfferEntity newOffer) {
		newOffer.setId(new Random().nextInt());
		offers.add(newOffer);
		return newOffer;
	}

	/*
	 * 3.5 kreirati REST endpoint koji omogućava izmenu postojeće ponude • putanja
	 * /project/offers/{ • ukoliko je prosleđen ID koji ne pripada nijednoj ponudi
	 * treba da vrati null , a u suprotnom vraća podatke ponude sa izmenjenim
	 * vrednostima • NAPOMENA : u okviru ove metode ne menjati vrednost atributa
	 * offer status
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public OfferEntity changeOneOffer(@RequestBody OfferEntity changeOffer, @PathVariable Integer id) {
		for (OfferEntity offer : getDB()) {
			if (offer.getId().equals(id)) {
				if (changeOffer.getOfferName() != null)
					offer.setOfferName(changeOffer.getOfferName());
				if (changeOffer.getOfferDescription() != null)
					offer.setOfferDescription(changeOffer.getOfferDescription());
				if (changeOffer.getOfferCreated() != null)
					offer.setOfferCreated(changeOffer.getOfferCreated());
				if (changeOffer.getOfferExpires() != null)
					offer.setOfferExpires(changeOffer.getOfferExpires());
				if (changeOffer.getRegularPrice() != null)
					offer.setRegularPrice(changeOffer.getRegularPrice());
				if (changeOffer.getActionPrice() != null)
					offer.setActionPrice(changeOffer.getActionPrice());
				if (changeOffer.getImagePath() != null)
					offer.setImagePath(changeOffer.getImagePath());
				if (changeOffer.getAvailableOffers() != null)
					offer.setAvailableOffers(changeOffer.getAvailableOffers());
				if (changeOffer.getBoughtOffers() != null)
					offer.setBoughtOffers(changeOffer.getBoughtOffers());
				return offer;
			}
		}
		return null;

	}

	/*
	 * 3.6 kreirati REST endpoint koji omogućava brisanje postojeće ponude • putanja
	 * /project/offers/{ • ukoliko je prosleđen ID koji ne pripada nijednoj ponudi
	 * metoda treba da vrati null , a u suprotnom vraća podatke o ponudi koja je
	 * obrisana
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public OfferEntity deleteOffer(@PathVariable Integer id) {
		Iterator<OfferEntity> it = getDB().iterator();
		while (it.hasNext()) {
			OfferEntity offer = it.next();
			if (offer.getId().equals(id)) {
				it.remove();
				return offer;
			}

		}
		return null;
	}

	/*
	 * 3.7 kreirati REST endpoint koji vraća ponudu po vrednosti prosleđenog ID a •
	 * putanja /project/offers/{ • u slučaju da ne postoji ponuda sa traženom
	 * vrednošću ID a vratiti null
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public OfferEntity getOneOffer(@PathVariable Integer id) {
		for (OfferEntity offer : getDB()) {
			if (offer.getId().equals(id))
				return offer;
		}
		return null;

	}

	/*
	 * 3.8 kreirati REST endpoint koji omogućava promenu vrednosti atributa offer
	 * status postojeće ponude • putanja /project/ changeOffer /{id}/status/{ •
	 * ukoliko je prosleđen ID koji ne pripada nijednoj ponudi metoda treba da vrati
	 * null , a u suprotnom vraća podatke o ponudi koja je obrisana
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/changeOffer/{id}/status/{status}")
	public OfferEntity changeOfferStatus(@PathVariable Integer id, @PathVariable OfferStatus status) {
		for (OfferEntity offer : getDB()) {
			if (offer.getId().equals(id)) {
				offer.setOfferStatus(status);
				return offer;
			}

		}
		return null;
	}

	/*
	 * 3.9 kreirati REST endpoint koji omogućava pronalazak svih ponuda čija se
	 * akcijska cena nalazi u odgovarajućem rasponu • putanja /project/ findByPrice
	 * lowerPrice }/ upperPrice
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/findByPrice/{lowerPrice}/and/{upperPrice}")
	public List<OfferEntity> findByPrice(@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
		List<OfferEntity> ponude = new ArrayList<>();
		for (OfferEntity offer : getDB()) {
			if (Double.compare(offer.getActionPrice(), lowerPrice) > 0 && Double.compare(offer.getActionPrice(), upperPrice) < 0){
				ponude.add(offer);
			}
		}
		return ponude;
	}
}
