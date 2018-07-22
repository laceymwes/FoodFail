

## What
Food Fail is an Android application that allows users to view the  inspection history of any facility that handles food in Albuquerque, NM. Users can search for facilities by name and view the facility's exact location via an embedded Google Map.

## Why
The city of   Albuquerque has many publicly available data sets, including the health inspection records used in this application. This is an opportunity to turn that data into useful information for the citizens of Albuquerque. Consumers can use this application to see whether a restaurant is maintaining and clean and healthy environment for their food preparation.

## Current State
#### What works?
- Users can navigate to the search view, and search for facilities by name.
- List items in the search view are clickable and take users to a detail view.
- Embedded map in the detail view successfully locates and marks facility.
- All inspection records for the selected facility are display under the map.
- Users can navigate to the local facilities view to see facilities located in their current postal code.
- Device location is successfully extracted when another application has an active client session with the Android OS LocationManager.

#### What doesn't work? (Yet!)
- Database pre-loading is inconsistent, meaning not all facilities and their inspection records are successfully loaded.
- Debugging shows successful reverse Google Geocoding API calls (provide location lat/lng) to extract postal code, and successful database query utilizing said postal code. ViewPager has no facilities to display due to record truncating issue in the database pre-load callback method.
- Undesirable upward navigation behavior when user uses navigation drawer from the search/detail view. Negatively impacts user experience.
- Application is only able to retrieve the current location of the device if another application has an active client session with the LocationManager. Location feature is testable with Google Maps running in background.

## Testing
The application has been tested with Android API levels 25 through 27 on the Nexus 6 and Pixel emulator hardware profiles. Final testing has been conducted using the Essential Phone (PH-1).


## Dependencies
- [Gson v 2.8.5](https://github.com/google/gson)
- [Retrofit v 2.4.0](https://square.github.io/retrofit/)
- [Apache Commons CSV v 1.5](https://commons.apache.org/proper/commons-csv/)
- [Stetho v 1.5.0](https://facebook.github.io/stetho/)

## Services Utilized
- [Google Geocoding](https://developers.google.com/maps/documentation/javascript/geocoding)
- [Google Maps](https://cloud.google.com/maps-platform/)

## Future Design Changes
1. Replace navigation drawer with bottom tabbed navigation.
2. Custom Food Fail logo.
3. Improved transitions.

## Future Functionality and Features
1. Make application completely network dependent. Move database to 3rd party hosting. All data will be retrieved utilizing web services (custom APIs).
2. Utilize [Yelp APIs](https://www.yelp.com/developers) to provide users consumer experience reviews in contrast to health inspection history.
3. Implement intents for Google Maps (or other GPS applications) so users can be directed to a selected facility.

## Planning
- [User Stories and Wireframes](planning/User_Story_Frame.pdf)

## Data
- [Entity Relationship Diagram (ERD)](database/ERD.pdf)
- [Data Definition Language (DDL)](database/ddl.sql)

## Documentation
[Javadoc](docs/api/index.html)

## Build  and Usage Instructions

#### Build
__Note__: Since I am unable to upload the CSV data set to GitHub due to size constraints, before cloning this repository please download [this file](http://data.cabq.gov/business/foodinspections/FoodInspectionsCurrentFY-en-us.csv). Open it in your spreadsheet program and save as "CSV".
1. Navigate to the [Food Fail GitHub repository](https://github.com/laceymwes/FoodFail) and select "Clone or download" on the right-hand side, at the top of the page.
2. Copy the SSH .git repository link from the drop-down dialogue.
3. Open IntelliJ and select "Check out form version control".
4. Paste the .git repository link copied in step 2 and select "clone".
5. Select "No" on the Check Out From Version Control prompt.
6. Select "Import Project", and select "Gradle", then "Next".
7. If prompted to overwrite file, select yes.
8. Open the buid.gradle script and specify the path for the .properties file containing the necessary API key.
9. After a successful sync, select move the previously downloaded CSV data set to the app/src/main/res/raw/ directory of the project. Save it as inspection_records.csv.
10. Select Build/Build from the top menu items.

#### Use
__Note__: Project min SDK version is Android API 21. Please use an emulator  or device with API 21 or higher installed when attempting to run this application. In order for Android to provide location for the Local Facilities view, you will need to run the application on a physical device.
1. After a successful build select Run/Edit configurations from the top menu items.
2. Select the plus icon and "Android App" from the top left of the dialogue.
3. Select "app" from the Module drop-down menu.
3. Select "OK" at the bottom. (You can name the configuration whatever you like)
4. Select Run/Run from the top menu items. (Due to current pre-population challenges, let the application sit at the landing page for 30 seconds or more)
5. Application usage and navigation is fairly straight-forward.
6. Use the side nav drawer to view facilities in your area, or search for facilities by name.
7. Clicking on a facility in the Search view will take you to a detail view, where you can see inspection records for the selected facility.
8. Scroll left and right to view each inspection record. 

## Licensing
[Food Fail and third part library licenses](License.md)
