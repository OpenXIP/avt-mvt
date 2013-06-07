/*
Copyright (c) 2010, Siemens Corporate Research a Division of Siemens Corporation 
All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.siemens.cmiv.avt.mvt.core;


import java.util.EventListener;

import com.siemens.cmiv.avt.mvt.ad.ADRetrieveEvent;
import com.siemens.cmiv.avt.mvt.ad.ADSearchEvent;

/**
 * @author Jie Zheng
 *
 */
public interface MVTListener extends EventListener{
	public void searchResultsAvailable(ADSearchEvent e);
	public void retriveResultsAvailable(ADRetrieveEvent e);
	public void calculateResultsAvailable(MVTCalculateEvent e);
}
