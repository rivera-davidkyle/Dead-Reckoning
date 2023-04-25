# Dead-Reckoning
## I. Goal
The goal of the problem is to develop an application that uses low-power sensors, such as the accelerometer, 
in combination with GPS to provide real-time location tracking. The main challenge is to minimize the use of GPS, which can be power-hungry, 
by developing an algorithm that combines the accelerometer data with GPS data to track the user's location accurately. 
This assignment aims to test the ability to build a low-energy tracking application and implement algorithms that can 
efficiently use sensors and GPS data to provide real-time tracking information.

## II. Life Cycle
The app starts by initializing the sensor and accelerometer and requesting location permission from the user. 
Then, the location manager is initialized, and the SupportMapFragment is retrieved to initialize the map.

Once the map is ready, the last known location is retrieved, and a marker is placed on the map at the user's current location. 
The onSensorChanged function updates the device's position based on the filtered accelerometer values, and the onLocationChanged 
function updates the device's position based on the GPS data.

The app uses a handler to execute the LocTask class every 10 seconds, which updates the marker on the map with the new location coordinates.

In summary, the app lifecycle is as follows:

    1. onCreate() is called, initializing the accelerometer and location manager, and requesting permission from the user.
    2. onMapReady() is called, initializing the map and placing a marker at the user's current location.
    3. onSensorChanged() is called whenever there is a change in the accelerometer data, updating the device's position.
    4. onLocationChanged() is called whenever there is a change in the GPS data, updating the device's position.
    5. The LocTask class is executed every 10 seconds, updating the marker on the map with the new location coordinates.

## III. Algorithm
The algorithm for location calculation in the given code is based on GPS and accelerometer data.
The GPS location data is obtained using the LocationManager and LocationListener classes. 
The last known location is obtained using the getLastKnownLocation method of LocationManager. 
This location is used to set the initial marker on the map. Therefore, the GPS data is only used once in this algorithm, making it efficient power-wise.
A handler is used to update the location periodically. The LocTask class implements the Runnable interface and updates the location every 10 seconds. 
It calculates the new latitude and longitude using the Haversine formula, which takes into account the curvature of the earth's surface. 
The approximation of Haversine formula calculates the great-circle distance between two points on a sphere, given their longitudes and latitudes. 
It is used to calculate the new location based on the accelerometer data. The formula uses the radius of the earth (rad), which is set to 6378.137m 
in the code. A complementary filter is applied to the accelerometer data to reduce noise and obtain a more accurate estimate of the velocity and position.

The filtered accelerometer data is used to calculate the velocity and position using the standard kinematic equations of motion.

Overall, the algorithm combines GPS and accelerometer data to obtain a more accurate estimate of the user's location. The GPS data is used as the initial estimate, and the accelerometer data is used to update the estimate periodically.
