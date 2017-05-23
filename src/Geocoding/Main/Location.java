package Geocoding.Main;

/**
 * Created by alistair on 27/01/2017.
 */
class Location {

    private final String name;

    String streetName;

    String townName;

    String stateName;

    String countryName;

    boolean sufficient = true;

    Location(String name) {
        this.name = name;
        String[] split = name.split(",");
        String country = split[split.length-1];
        if (country.equals("USA")) parseStringUSA(split);
        else parseStringUK(split);
    }

    private String[] streetNames = {"road","avenue","lane","street"};

    private boolean checkStreetName(String s) {
        for (String s1 : streetNames) {
            if (s.toLowerCase().contains(s1)) return true;
        }
        return false;
    }

    void parseStringUSA(String[] strings) {
        this.countryName = "USA";
        if (strings.length == 4) {
            this.streetName = strings[0];
            this.townName = strings[1];
            this.stateName = strings[2];
        }
        else if (strings.length == 3) {
            this.stateName = strings[1];
            if (checkStreetName(strings[0])) this.streetName = strings[0];
            else this.townName = strings[0];
        }
        else sufficient = false;
    }

    void parseStringUK(String[] strings) {
        this.countryName = "UK";
        if (strings.length == 3) {
            this.streetName = strings[0];
            this.townName = strings[1];
        }
        else if (strings.length == 4) {
            if (checkStreetName(strings[1])) {
                this.streetName = strings[1];
                this.townName = strings[2];
            }
            else {
                this.streetName = strings[0];
                this.townName = (strings[1] + ' ' + strings[2]);
            }
        }
        else sufficient = false;
    }

}
