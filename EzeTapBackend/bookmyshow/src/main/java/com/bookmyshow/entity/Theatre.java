/**
 * 
 */
package com.bookmyshow.entity;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author jai12
 *
 */
@Entity
@Table(name = "Theatre")
public class Theatre {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "theatre_id")
	private long Id;
	
	@Column(name = "theatre_name")
	private String theatreName;
	
	@Column(name = "theatre_address")
	private String theatreAddress;
	
	@Column(name = "seat_count")
	private long seatCount;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "movie_theatre_price",
	joinColumns = @JoinColumn(name = "theatre_id"),
	inverseJoinColumns = @JoinColumn(name = "price_id"))
	private Set<TheatrePrice> moviePrice;
	
	@OneToMany(mappedBy = "movieTheatreTimingId.theatre")
	private Set<MovieTheatreTiming> movieTimingList;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="location_id")
	private Location location;
	
	public long getId() {
		return Id;
	}

	/**
	 * 
	 */
	public Theatre() {
		super();
	}

	public void setId(long id) {
		Id = id;
	}

	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	public Set<TheatrePrice> getMoviePrice() {
		return moviePrice;
	}

	public void setMoviePrice(Set<TheatrePrice> moviePrice) {
		this.moviePrice = moviePrice;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public long getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(long seatCount) {
		this.seatCount = seatCount;
	}

	public String getTheatreAddress() {
		return theatreAddress;
	}

	public void setTheatreAddress(String theatreAddress) {
		this.theatreAddress = theatreAddress;
	}

	public Set<MovieTheatreTiming> getMovieTimingList() {
		return movieTimingList;
	}

	public void setMovieTimingList(Set<MovieTheatreTiming> movieTimingList) {
		this.movieTimingList = movieTimingList;
	}


	
	
	
}
