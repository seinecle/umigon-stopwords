# Umigon
A family of modules for essential NLP tasks and sentiment analysis, done well.

# umigon-stopwords
Stopwords in many languages and sophisticated stopwords removal methods for ngrams.

# Releases
**Maven**

<dependency>
    <groupId>net.clementlevallois.functions</groupId>
    <artifactId>umigon-stopwords</artifactId>
    <version>put the latest version here</version>
</dependency>

**Gradle**

implementation group: 'net.clementlevallois.functions', name: 'umigon-stopwords', version: 'put the latest version here'

* 2023, March 28: version 0.12

Fixes a critical issue with files containing stopwords being ignored because of a malformed pom. Moving the resource files to the resources folder is a better practice.

* 2023, March 28: version 0.11

Adds 'https' as an annoying stopword to remove (English and French only at the moment)

* 2023, March 24: version 0.10

Initial release


# Lists of stopwords
- in multiple languages  
- with lists of stopwords for academic types of literature and Twitter


### Focus on the stopwords for the academic literature
These stopwords have been curated "by hand" across a long period of time. Terms are included if they are not specific to a scientific field. Please check if this fits your needs. There are some border cases. For instance, "network" is included, because I judged that the term carries less and less informational value (is becoming less and less specific to a given field or subfield). Users interested in studying corpora network theory / social networks / neural networks might still need to remove it from the list.

### Stopwords for the academic literature in French
This list is currently under construction, but works already pretty well.

### Use
These lists are extremely useful when following a ngram type of approach in topic detection or semantic network analysis.

### Origin?
This function is developed by Clement Levallois, in support of academic work published [in various places](https://scholar.google.fr/citations?user=r0R0vekAAAAJ&hl=en). It is now used in support of [a web app providing free text analysis for non coders](https://nocodefunctions.com).

### Credit and attribution
If you use this repository in an academic context, please cite the publication where these stopwords where first deployed:

> Levallois, C., Clithero, J. A., Wouters, P., Smidts, A., & Huettel, S. A. (2012). Translating upwards: linking the neural and social sciences via neuroeconomics. Nature Reviews Neuroscience, 13(11), 789-797.

Otherwise, please add a link to this repo in your app.

### Contributions
Your contributions are very welcome.

### License
Creative Commons Attribution 4.0 International Public License
