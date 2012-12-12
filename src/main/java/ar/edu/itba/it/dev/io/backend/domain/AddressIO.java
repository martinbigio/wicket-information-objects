package ar.edu.itba.it.dev.io.backend.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class AddressIO {
	
    @Column(length=100) @Size(max=100)
	public String street;
	
	@Column(length=10) @Size(max=10)
	public String number;
	
	@Column(length=10) @Size(max=10)
	public String floor;
	
	@Column(length=10) @Size(max=10)
	public String department;
	
	@Column(length=300) @Size(max=300)
	public String city;
	
	@Column(length=40) @Size(max=40)
	public String phoneNumber;
	
	@Column(length=10) @Size(max=10)
	public String zipCode;
}
