/**
 * 
 */
package com.bookmyshow.entity;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author jai12
 *
 */
@Entity
@Table(name = "Theatre_Price")
public class TheatrePrice {

	@Id
	@Column(name = "price_id")
	private long Id;
	
	@Column(name = "Seat_Type")
	private String seatType;
	
	@Column(name = "Seat_Price")
	private long seatPrice;

	@ManyToMany(mappedBy = "moviePrice")
	private Set<Theatre> theatreList;
	
	
	/**
	 * 
	 */
	public TheatrePrice() {
		super();
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public long getSeatPrice() {
		return seatPrice;
	}

	public void setSeatPrice(long seatPrice) {
		this.seatPrice = seatPrice;
	}

	public Set<Theatre> getTheatreList() {
		return theatreList;
	}

	public void setTheatreList(Set<Theatre> theatreList) {
		this.theatreList = theatreList;
	}


		
}
