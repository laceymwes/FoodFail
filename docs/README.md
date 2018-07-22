# __Food Fail__

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


## Build Instructions


## Usage Instructions

## Licensing
