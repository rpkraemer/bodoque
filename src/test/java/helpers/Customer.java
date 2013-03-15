package helpers;
import java.util.Date;


public class Customer extends Person {

	private Date dtCadastro;
	private String address;
	
	public Customer(String name, int age) {
		super(name, age);
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public String getAddress() {
		return address;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

}
