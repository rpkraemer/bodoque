package helpers;

import br.bodoque.PrevalentObject;

public class Person extends PrevalentObject<Person> {

	private String name;
	private int age;
	private Dog dog;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Dog getDog() {
		return dog;
	}
	public void setDog(Dog dog) {
		this.dog = dog;
	}
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

}
