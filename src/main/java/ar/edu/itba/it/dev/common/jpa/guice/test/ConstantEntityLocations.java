package ar.edu.itba.it.dev.common.jpa.guice.test;


public class ConstantEntityLocations implements EntityLocations {
	private final String[] locations;
	
	public ConstantEntityLocations(String... locations) {
		super();
		this.locations = locations;
	}

	@Override
	public String[] getEntityPackages() {
		return locations;
	}

}
