Welcome to the AVT Measurement Variability Tool&trade; Project!
===============================================================

The Algorithm Validation Toolkit&trade; (AVT&trade;) Open Source project is a set
of tools that facilitate the testing and statistical comparison of image processing
algorithms.  It is built upon the
[eXtensible Imaging Platform&trade; (XIP&trade;)](http://www.OpenXIP.org)
development framework, and can consume objects based on
[AIM (Annotation and Image Markup)] (https://github.com/NCIP/annotation-and-image-markup)
models.  

The Measurement Variability Tool&trade; (MVT&trade;) component of AVT&trade; is an XIP-based 
[DICOM Hosted Application](http://medical.nema.org/Dicom/2011/11_19pu.pdf)
for doing statistical analysis of data held in AIM objects.  MVT can be run using a
[DICOM Hosting System](http://medical.nema.org/Dicom/2011/11_19pu.pdf),
such as the [XIP Host&trade;](https://github.com/OpenXIP/xip-host).
MVT&trade; is implemented in Java with Swing, and utilizes scene graphs
created with the [XIP Libraries&trade;](https://github.com/OpenXIP/xip-libraries).
It also utilizes an external program, such as the [R server](http://www.r-project.org/), 
as a statistical calculation engine.

The ultimate goals of the project include:

* Provide mechanisms to parse AIM objects, dividing observations into
  dependent and independent variables to provide to the statistical analysys.
* Provide a mechanism to identify the set of data to be used as the 
  nominal ground truth for a statistical analysis.
* Provide mechanisms for running statistical calculation scripts for comparing
  sets of measurements against each other and against the nominal ground truth.
* Provide mechanisms to visualize the results of calculations.
* Provide mechanisms to identify and investigate outliers, including viewing
  the related DICOM dataset, possibly excluding the outliers from the
  statistical calculations.

AVT&trade;, including AVT MVT&trade; is distributed under the
[Apache 2.0 License](http://opensource.org/licenses/Apache-2.0).
Please see the NOTICE and LICENSE files for details.

You will find more details about AVT&trade; and XIP&trade; in the following links:

*  [Home Page] (http://www.OpenXIP.org)
*  [Forum/Mailing List] (https://groups.google.com/forum/?fromgroups#!forum/openxip)
*  [Issue tracker] (https://plans.imphub.org/browse/XIP)
*  [Documentation] (https://docs.imphub.org/display/XIP)
*  [Git code repository] (https://github.com/OpenXIP/avt-mvt)
*  [Windows installer](https://mirgforge.wustl.edu/gf/project/xip/frs/)
