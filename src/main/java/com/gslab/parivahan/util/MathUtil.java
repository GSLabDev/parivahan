/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gslab.parivahan.util;

import static java.lang.Math.*;

class MathUtil {
    static final double EARTH_RADIUS = 6371009;

    static double clamp(double x, double low, double high) {
        return x < low ? low : (x > high ? high : x);
    }
    
    static double wrap(double n, double min, double max) {
        return (n >= min && n < max) ? n : (mod(n - min, max - min) + min);
    }

    static double mod(double x, double m) {
        return ((x % m) + m) % m;
    }

    static double mercator(double lat) {
        return log(tan(lat * 0.5 + PI/4));
    }

    static double inverseMercator(double y) {
        return 2 * atan(exp(y)) - PI / 2;
    }
    
    static double hav(double x) {
        double sinHalf = sin(x * 0.5);
        return sinHalf * sinHalf;
    }

    static double arcHav(double x) {
        return 2 * asin(sqrt(x));
    }
    
    static double sinFromHav(double h) {
        return 2 * sqrt(h * (1 - h));
    }

    static double havFromSin(double x) {
        double x2 = x * x;
        return x2 / (1 + sqrt(1 - x2)) * .5;
    }

    static double sinSumFromHav(double x, double y) {
        double a = sqrt(x * (1 - x));
        double b = sqrt(y * (1 - y));
        return 2 * (a + b - 2 * (a * y + b * x));
    }

    static double havDistance(double lat1, double lat2, double dLng) {
        return hav(lat1 - lat2) + hav(dLng) * cos(lat1) * cos(lat2);
    }
}
