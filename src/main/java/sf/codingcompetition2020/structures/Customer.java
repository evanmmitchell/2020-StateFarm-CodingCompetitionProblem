package sf.codingcompetition2020.structures;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Customer{
	private int customerId;
	private String firstName;
	private String lastName;
	private int age;
	private String area;
	private int agentId;
	private short agentRating;
	private String primaryLanguage;
	private List<Dependent> dependents;
	private boolean homePolicy;
	private boolean autoPolicy;
	private boolean rentersPolicy;
	private String totalMonthlyPremium;
	private short yearsOfService;
	private Integer vehiclesInsured;

	public Customer(int customerId,	String firstName,	String lastName, int age, String area, int agentId, short agentRating, String primaryLanguage, List<Dependent> dependents, boolean homePolicy, boolean autoPolicy, boolean rentersPolicy,	String totalMonthlyPremium,	short yearsOfService,	Integer vehiclesInsured) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.area = area;
		this.agentId = agentId;
		this.agentRating = agentRating;
		this.primaryLanguage = primaryLanguage;
		this.dependents = dependents;
		this.homePolicy = homePolicy;
		this.autoPolicy = autoPolicy;
		this.rentersPolicy = rentersPolicy;
		this.totalMonthlyPremium = totalMonthlyPremium;
		this.yearsOfService = yearsOfService;
		this.vehiclesInsured = vehiclesInsured;
	}

	public int getCustomerId() {
		return customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public int getAge() {
		return age;
	}
	public String getArea() {
		return area;
	}
	public int getAgentId() {
		return agentId;
	}
	public short getAgentRating() {
		return agentRating;
	}
	public String getPrimaryLanguage() {
		return primaryLanguage;
	}
	public List<Dependent> getDependents() {
		return dependents;
	}
	public boolean getHomePolicy() {
		return homePolicy;
	}
	public boolean getAutoPolicy() {
		return autoPolicy;
	}
	public boolean getRentersPolicy() {
		return rentersPolicy;
	}
	public String getTotalMonthlyPremium() {
		return totalMonthlyPremium;
	}
	public short getYearsOfService() {
		return yearsOfService;
	}
	public Integer getVehiclesInsured() {
		return vehiclesInsured;
	}
}
