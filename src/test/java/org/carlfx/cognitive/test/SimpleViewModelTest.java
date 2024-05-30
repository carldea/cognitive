/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright © 2023-2024 Carl Dea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.carlfx.cognitive.test;


import org.carlfx.cognitive.viewmodel.SimpleViewModel;
import org.carlfx.cognitive.viewmodel.ViewModel;

import java.util.List;
import java.util.Set;

public class SimpleViewModelTest {
    public static void main(String[] args){
        //TextField firstName
        ViewModel personVm = new SimpleViewModel()
                .addProperty("firstName", "Fred")
                .addProperty("age", 54l)
                .addProperty("height", 123)
                .addProperty("colors", Set.of("red", "blue"))
                .addProperty("foods", List.of("bbq", "chips", "bbq"))
                .addProperty("thing", new Object(){
                    @Override
                    public String toString() {
                        return "thing ";
                    }
                })
                .addProperty("mpg", 20.5f);
        log("--------------");
        log("Creation personVm \n" + personVm);

        log("--------------");
        personVm.setPropertyValue("firstName", "Mary");
        log("before save " + personVm.debugPropertyMessage("firstName"));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage("firstName"));
        log("--------------");
        personVm.setPropertyValue("age", 20);
        log("before save " + personVm.debugPropertyMessage("age"));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage("age"));
        log("--------------");
        personVm.setPropertyValue("height", 555);
        log("before save " + personVm.debugPropertyMessage("height"));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage("height"));
        log("--------------");
        personVm.setPropertyValue("colors", Set.of("green"));
        log("before save " + personVm.debugPropertyMessage("colors"));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage("colors"));

        // changing ("bbq", "chips", "bbq") TO ("corn", "crabs")
        personVm.setPropertyValues("foods", Set.of("corn", "crabs"));
        log("before save " + personVm.debugPropertyMessage("foods"));
        personVm.save(); // commit data from
        log("after save  " + personVm.debugPropertyMessage("foods"));


        log("--------------");

        ViewModel personVm2 = new SimpleViewModel()
                .addProperty("firstName", "Fred")
                .addProperty("age", 54l)
                .addProperty("height", 123)
                .addProperty("colors", Set.of("red", "blue"))
                .addProperty("foods", List.of("bbq", "chips", "bbq"))
                .addProperty("thing", new Object(){
                    @Override
                    public String toString() {
                        return "thing ";
                    }
                })
                .addProperty("mpg", 20.5f);


        personVm2.setPropertyValue("firstName", "Mary");
        personVm2.setPropertyValue("age", 20);
        personVm2.setPropertyValue("height", 555);
        personVm2.setPropertyValue("colors", Set.of("green"));
        personVm2.setPropertyValues("foods", Set.of("corn", "crabs"));
        personVm2.setPropertyValue("thing", new Object(){
            @Override
            public String toString() {
                return "thing 2";
            }
        });
        log("before reset personVm2 \n" + personVm2);
        personVm2.reset();
        log("after reset  personVm2 \n" + personVm2);

    }
    public static void log(String message) {
        System.out.println(message);
    }
}
