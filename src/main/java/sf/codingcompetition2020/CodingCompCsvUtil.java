package sf.codingcompetition2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import sf.codingcompetition2020.structures.Agent;
import sf.codingcompetition2020.structures.Claim;
import sf.codingcompetition2020.structures.Customer;
import sf.codingcompetition2020.structures.Vendor;
import sf.codingcompetition2020.structures.Dependent;

public class CodingCompCsvUtil {

	/* #1
	 * readCsvFile() -- Read in a CSV File and return a list of entries in that file.
	 * @param filePath -- Path to file being read in.
	 * @param classType -- Class of entries being read in.
	 * @return -- List of entries being returned.
	 */
	public <T> List<T> readCsvFile(String filePath, Class<T> classType) {
		List<T> result = new ArrayList<T>();
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
			String row = csvReader.readLine();
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",(?![^\\[]*\\])");
				if (classType == Agent.class) {
					T agent = (T) new Agent(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]);
					result.add(agent);
				} else if (classType == Claim.class) {
					T claim = (T) new Claim(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Boolean.parseBoolean(data[2]),
							Integer.parseInt(data[3]));
					result.add(claim);
				} else if (classType == Customer.class) {
					List<Dependent> dependentsList = new ArrayList<Dependent>();
					if (data[8].length() > 0) {
						String[] dependents = data[8].substring(1, data[8].length() - 1).split(",(?![^{]*})");
						for (String dependent : dependents) {
							String[] name = dependent.substring(1, dependent.length() - 1).split(",");
							String firstName = name[0].split("\"\"")[2];
							String lastName = name[1].split("\"\"")[2];
							dependentsList.add(new Dependent(firstName, lastName));
						}
					}

					T customer = (T) new Customer(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), data[4],
							Integer.parseInt(data[5]), Short.parseShort(data[6]), data[7], dependentsList,
							Boolean.parseBoolean(data[9]), Boolean.parseBoolean(data[10]), Boolean.parseBoolean(data[11]), data[12],
							Short.parseShort(data[13]), Integer.parseInt(data[14]));
					result.add(customer);
				} else if (classType == Vendor.class) {
					T vendor = (T) new Vendor(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]),
							Boolean.parseBoolean(data[3]));
					result.add(vendor);
				} else {
					return null;
				}
			}
			csvReader.close();
		} catch (IOException e) {
			return null;
		}
		return result;
	}

	/* #2
	 * getAgentCountInArea() -- Return the number of agents in a given area.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @return -- The number of agents in a given area
	 */
	public int getAgentCountInArea(String filePath, String area) {
		List<Agent> agents = readCsvFile(filePath, Agent.class);
		int result = 0;
		for (Agent agent : agents) {
			if (agent.getArea().equals(area)) {
				result++;
			}
		}
		return result;
	}

	/* #3
	 * getAgentsInAreaThatSpeakLanguage() -- Return a list of agents from a given area, that speak a certain language.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @param language -- The language spoken by the agent(s).
	 * @return -- The number of agents in a given area
	 */
	public List<Agent> getAgentsInAreaThatSpeakLanguage(String filePath, String area, String language) {
		List<Agent> agents = readCsvFile(filePath, Agent.class);
		List<Agent> qualifiedAgents = new ArrayList<Agent>();
		for (Agent agent : agents) {
			if (agent.getArea().equals(area) && agent.getLanguage().equals(language)) {
				qualifiedAgents.add(agent);
			}
		}
		return qualifiedAgents;
	}

	/* #4
	 * countCustomersFromAreaThatUseAgent() -- Return the number of individuals from an area that use a certain agent.
	 * @param filePath -- Path to file being read in.
	 * @param customerArea -- The area from which the customers should be counted.
	 * @param agentFirstName -- First name of agent.
	 * @param agentLastName -- Last name of agent.
	 * @return -- The number of customers that use a certain agent in a given area.
	 */
	public short countCustomersFromAreaThatUseAgent(Map<String, String> csvFilePaths, String customerArea,
			String agentFirstName, String agentLastName) {
		List<Customer> customers = readCsvFile(csvFilePaths.get("customerList"), Customer.class);
		List<Agent> agents = readCsvFile(csvFilePaths.get("agentList"), Agent.class);
		short result = 0;
		for (Customer customer : customers) {
			if (customer.getArea().equals(customerArea)) {
				int agentId = customer.getAgentId();
				for (Agent agent : agents) {
					if (agent.getAgentId() == agentId && agent.getFirstName().equals(agentFirstName)
							&& agent.getLastName().equals(agentLastName)) {
						result++;
					}
				}
			}
		}
		return result;
	}

	/* #5
	 * getCustomersRetainedForYearsByPlcyCostAsc() -- Return a list of customers retained for a given number of years, in ascending order of their policy cost.
	 * @param filePath -- Path to file being read in.
	 * @param yearsOfServeice -- Number of years the person has been a customer.
	 * @return -- List of customers retained for a given number of years, in ascending order of policy cost.
	 */
	public List<Customer> getCustomersRetainedForYearsByPlcyCostAsc(String customerFilePath, short yearsOfService) {
		List<Customer> customers = readCsvFile(customerFilePath, Customer.class);
		List<Customer> qualifiedCustomers = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if (customer.getYearsOfService() == yearsOfService) {
				qualifiedCustomers.add(customer);
			}
		}
		Comparator<Customer> compareByPremium = new Comparator<Customer>() {
			public int compare(Customer c1, Customer c2) {
				return c1.getTotalMonthlyPremium().compareTo(c2.getTotalMonthlyPremium());
			}
		};
		qualifiedCustomers.sort(compareByPremium);
		return qualifiedCustomers;
	}

	/* #6
	 * getLeadsForInsurance() -- Return a list of individuals who’ve made an inquiry for a policy but have not signed up.
	 * *HINT* -- Look for customers that currently have no policies with the insurance company.
	 * @param filePath -- Path to file being read in.
	 * @return -- List of customers who’ve made an inquiry for a policy but have not signed up.
	 */
	public List<Customer> getLeadsForInsurance(String filePath) {
		List<Customer> customers = readCsvFile(filePath, Customer.class);
		List<Customer> qualifiedCustomers = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if (!customer.getAutoPolicy() && !customer.getHomePolicy() && !customer.getRentersPolicy()) {
				qualifiedCustomers.add(customer);
			}
		}
		return qualifiedCustomers;
	}

	/* #7
	 * getVendorsWithGivenRatingThatAreInScope() -- Return a list of vendors within an area and include options to narrow it down by:
			a.	Vendor rating
			b.	Whether that vendor is in scope of the insurance (if inScope == false, return all vendors in OR out of scope, if inScope == true, return ONLY vendors in scope)
	 * @param filePath -- Path to file being read in.
	 * @param area -- Area of the vendor.
	 * @param inScope -- Whether or not the vendor is in scope of the insurance.
	 * @param vendorRating -- The rating of the vendor.
	 * @return -- List of vendors within a given area, filtered by scope and vendor rating.
	 */
	public List<Vendor> getVendorsWithGivenRatingThatAreInScope(String filePath, String area, boolean inScope,
			int vendorRating) {
		List<Vendor> vendors = readCsvFile(filePath, Vendor.class);
		List<Vendor> qualifiedVendors = new ArrayList<Vendor>();
		for (Vendor vendor : vendors) {
			if (vendor.getArea().equals(area) && vendor.getVendorRating() >= vendorRating && ((inScope && vendor.getInScope()) || !inScope)) {
				qualifiedVendors.add(vendor);
			}
		}
		return qualifiedVendors;
	}

	/* #8
	 * getUndisclosedDrivers() -- Return a list of customers between the age of 40 and 50 years (inclusive), who have:
			a.	More than X cars
			b.	less than or equal to X number of dependents.
	 * @param filePath -- Path to file being read in.
	 * @param vehiclesInsured -- The number of vehicles insured.
	 * @param dependents -- The number of dependents on the insurance policy.
	 * @return -- List of customers filtered by age, number of vehicles insured and the number of dependents.
	 */
	public List<Customer> getUndisclosedDrivers(String filePath, int vehiclesInsured, int dependents) {
		List<Customer> customers = readCsvFile(filePath, Customer.class);
		List<Customer> qualifiedCustomers = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if (customer.getVehiclesInsured() > vehiclesInsured && customer.getAge() >= 40 && customer.getAge() <= 50
					&& customer.getDependents().size() <= dependents) {
				qualifiedCustomers.add(customer);
			}
		}
		return qualifiedCustomers;
	}

	/* #9
	 * getAgentIdGivenRank() -- Return the agent with the given rank based on average customer satisfaction rating.
	 * *HINT* -- Rating is calculated by taking all the agent rating by customers (1-5 scale) and dividing by the total number
	 * of reviews for the agent.
	 * @param filePath -- Path to file being read in.
	 * @param agentRank -- The rank of the agent being requested.
	 * @return -- Agent ID of agent with the given rank.
	 */
	public int getAgentIdGivenRank(String filePath, int agentRank) {
		List<Customer> customers = readCsvFile(filePath, Customer.class);
		Map<Integer, List<Short>> ratings = new HashMap<Integer, List<Short>>();
		for (Customer customer : customers) {
			List<Short> agentRatings = ratings.get(customer.getAgentId());
			if (agentRatings == null) {
				agentRatings = new ArrayList<Short>();
			}
			agentRatings.add(customer.getAgentRating());
			ratings.put(customer.getAgentId(), agentRatings);
		}

		Iterator<Entry<Integer, List<Short>>> it = ratings.entrySet().iterator();
		List<List<Float>> averageRatings = new ArrayList<List<Float>>();
    while (it.hasNext()) {
			Entry<Integer, List<Short>> entry = it.next();
			int sum = 0;
			for (Short rating : entry.getValue()) {
				sum += rating;
			}
			float averageRating = sum / (float) entry.getValue().size();
			averageRatings.add(Arrays.asList((float) entry.getKey(), averageRating));
		}

		Comparator<List<Float>> compareByAverageRating = new Comparator<List<Float>>() {
			public int compare(List<Float> l1, List<Float> l2) {
				return l2.get(1).compareTo(l1.get(1));
			}
		};

		averageRatings.sort(compareByAverageRating);
		return Math.round(averageRatings.get(agentRank - 1).get(0));
	}


	/* #10
	 * getCustomersWithClaims() -- Return a list of customers who’ve filed a claim within the last <numberOfMonths> (inclusive).
	 * @param filePath -- Path to file being read in.
	 * @param monthsOpen -- Number of months a policy has been open.
	 * @return -- List of customers who’ve filed a claim within the last <numberOfMonths>.
	 */
	public List<Customer> getCustomersWithClaims(Map<String,String> csvFilePaths, short monthsOpen) {
		List<Claim> claims = readCsvFile(csvFilePaths.get("claimList"), Claim.class);
		List<Customer> customers = readCsvFile(csvFilePaths.get("customerList"), Customer.class);
		Set<Customer> qualifiedCustomers = new HashSet<Customer>();
		for (Claim claim : claims) {
			if (claim.getMonthsOpen() <= monthsOpen) {
				for (Customer customer : customers) {
					if (claim.getCustomerId() == customer.getCustomerId()) {
						qualifiedCustomers.add(customer);
					}
				}
			}
		}
		return new ArrayList<Customer>(qualifiedCustomers);
	}

}
