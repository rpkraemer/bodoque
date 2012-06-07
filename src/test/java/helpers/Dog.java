package helpers;

import br.bodoque.PrevalentObject;

public class Dog extends PrevalentObject<Dog> {

	private final String name;
	
	public Dog(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	protected Dog whoAmI() {
		return this;
	}
}
