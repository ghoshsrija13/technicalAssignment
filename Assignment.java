package com.java.practice;

import java.util.*;

// Interface for parsing data
interface DataParser {
    List<CustomerData> parse(String input);
}

// Interface for generating reports
interface ReportGenerator {
    void generateReport(List<CustomerData> customerDataList);
}

// Customer data
class CustomerData {
    private String customerId;
    private String contractId;
    private String geozone;
    private String teamcode;
    private String projectcode;
    private String buildduration;

    public CustomerData(String customerId, String contractId, String geozone, String teamcode, String projectcode, String buildduration) {
        this.customerId = customerId;
        this.contractId = contractId;
        this.geozone = geozone;
        this.teamcode = teamcode;
        this.projectcode = projectcode;
        this.buildduration = buildduration;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getGeozone() {
        return geozone;
    }

    public void setGeozone(String geozone) {
        this.geozone = geozone;
    }

    public String getTeamcode() {
        return teamcode;
    }

    public void setTeamcode(String teamcode) {
        this.teamcode = teamcode;
    }

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    public String getBuildduration() {
        return buildduration;
    }

    public void setBuildduration(String buildduration) {
        this.buildduration = buildduration;
    }

    @Override
    public String toString() {
        return customerId;
    }
}

// Input data parser implementing DataParser interface
class StringDataParser implements DataParser {

    public List<CustomerData> parse(String input) {
        List<CustomerData> customerDataList = new ArrayList<>();
        String[] lines = input.split("\n");
        for (String line : lines) {
            String[] parts = line.split(",");
            CustomerData customerData = new CustomerDataBuilder()
                    .withCustomerId(parts[0])
                    .withContractId(parts[1])
                    .withGeozone(parts[2])
                    .withTeamcode(parts[3])
                    .withProjectcode(parts[4])
                    .withBuildduration(parts[5])
                    .build();
            customerDataList.add(customerData);
        }
        return customerDataList;
    }
}

// Custom report generator implementing ReportGenerator interface
class CustomReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(List<CustomerData> customerDataList) {
        Map<String, Set<String>> contractCustomerMap = new HashMap<>();
        Map<String, Set<String>> geozoneCustomerMap = new HashMap<>();
        Map<String, List<Integer>> geozoneBuildTimeMap = new HashMap<>();

        for (CustomerData customerData : customerDataList) {
            // Unique customerId for each contractId
            String contractId = customerData.getContractId();
            if (!contractCustomerMap.containsKey(contractId)) {
                contractCustomerMap.put(contractId, new HashSet<String>());
            }
            contractCustomerMap.get(contractId).add(customerData.toString());

            // Unique customerId for each geozone
            String geozone = customerData.getGeozone();
            if (!geozoneCustomerMap.containsKey(geozone)) {
                geozoneCustomerMap.put(geozone, new HashSet<String>());
            }
            geozoneCustomerMap.get(geozone).add(customerData.toString());

            // Average buildduration for each geozone
            String buildduration = customerData.getBuildduration();
            int buildTime = Integer.parseInt(buildduration.substring(0, buildduration.length() - 1));
            if (!geozoneBuildTimeMap.containsKey(geozone)) {
                geozoneBuildTimeMap.put(geozone, new ArrayList<Integer>());
            }
            geozoneBuildTimeMap.get(geozone).add(buildTime);
        }

        System.out.println("** The number of unique customerId for each contractId:");
        for (Map.Entry<String, Set<String>> entry : contractCustomerMap.entrySet()) {
            System.out.println("ContractId: " + entry.getKey() + ", Unique Customers: " + entry.getValue().size());
        }

        System.out.println("** The number of unique customerId for each geozone:");
        for (Map.Entry<String, Set<String>> entry : geozoneCustomerMap.entrySet()) {
            System.out.println("Geozone: " + entry.getKey() + ", Unique Customers: " + entry.getValue().size());
        }

        System.out.println("** The average buildduration for each geozone:");
        for (Map.Entry<String, List<Integer>> entry : geozoneBuildTimeMap.entrySet()) {
            List<Integer> buildTimes = entry.getValue();
            double totalBuildTime = 0;
            for (Integer time : buildTimes) {
                totalBuildTime += time;
            }
            double average = totalBuildTime / buildTimes.size();
            System.out.println("Geozone: " + entry.getKey() + ", Average Build Duration: " + average + "s");
        }

        System.out.println("** The list of unique customerId for each geozone:");
        for (Map.Entry<String, Set<String>> entry : geozoneCustomerMap.entrySet()) {
            System.out.println("Geozone: " + entry.getKey() + ", Customers: " + entry.getValue());
        }
    }
}

// CustomerDataBuilder for constructing CustomerData objects
class CustomerDataBuilder {
    private String customerId;
    private String contractId;
    private String geozone;
    private String teamcode;
    private String projectcode;
    private String buildduration;

    public CustomerDataBuilder() {
        // Initialize with default values if needed
    }

    public CustomerDataBuilder withCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public CustomerDataBuilder withContractId(String contractId) {
        this.contractId = contractId;
        return this;
    }

    public CustomerDataBuilder withGeozone(String geozone) {
        this.geozone = geozone;
        return this;
    }

    public CustomerDataBuilder withTeamcode(String teamcode) {
        this.teamcode = teamcode;
        return this;
    }

    public CustomerDataBuilder withProjectcode(String projectcode) {
        this.projectcode = projectcode;
        return this;
    }

    public CustomerDataBuilder withBuildduration(String buildduration) {
        this.buildduration = buildduration;
        return this;
    }

    public CustomerData build() {
        return new CustomerData(customerId, contractId, geozone, teamcode, projectcode, buildduration);
    }
}

public class Assignment {
    public static void main(String[] args) {
        // Input data
        String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s\n" +
                "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n" +
                "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n" +
                "1233456,2345,us_west,BlueTeam,ProjectDate,2221s\n" +
                "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";

        System.out.println("** User Data" + input +"\n");
        System.out.println("** Columns --" + "customerId,contractId,geozone,teamcode,projectcode,buildduration " +"\n");

        // Create instances of DataParser and ReportGenerator
        DataParser dataParser = new StringDataParser();
        ReportGenerator reportGenerator = new CustomReportGenerator();

        // Parse data
        List<CustomerData> customerDataList = dataParser.parse(input);

        // Generate report
        reportGenerator.generateReport(customerDataList);
    }
}
