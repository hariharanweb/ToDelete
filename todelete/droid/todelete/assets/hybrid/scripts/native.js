function success(response) {
	response = response.replace(/\\n/g, "");
	response = JSON.parse(response);	
	nativeContainer.addValue("trains", getTrains(response));		
}

function successStations(response){
	response = response.replace(/\\n/g, "");
	response = JSON.parse(response);
	nativeContainer.addValue("stations", getStations(response));
}

function fail(response) {
	nativeContainer.addValue("trains", {
		"key" : "AJAX FAILS"
	});
	nativeContainer.addValue("stations", {
		"key" : "AJAX FAILS"
	});
}

function fetchTrains(stationName, directionType) {

	stationName = stationName.replace(/\s/g, "-");
	calatrava.bridge.requests
			.issue(
					"http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url%3D%22http%3A%2F%2Fwww.thetrainline.com%2FLive%2F"
							+ directionType
							+ "%2F"
							+ stationName
							+ "%22%20and%20xpath%3D'%2F%2Fdiv%5B%40id%3D%22train-results%22%5D%2Ful%5B%40class%3D%22results-contents%22%5D%2Fli%2Fa'%3B&format=json",
					"GET", null, success, fail, null);
}

function fetchStations(link) {

	calatrava.bridge.requests
			.issue(
					"http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url%3D%22http%3A%2F%2F"+link+"%22%20and%20xpath%3D'%2F%2Ful%5B%40class%3D%22details-contents%22%5D%2Fli'%3B&format=json",
					"GET", null, successStations, fail, null);
}

function getTrains(myjson) {
	var result = [];
	var trainsJson = myjson.query.results.a;

	trainsJson.forEach(function(trainJson) {
		result.push(getTrain(trainJson));
	});
	return result;
}

function getTrain(trainJson) {
	var train = {};
	train.href = trainJson.href;
	trainJson.span.filter(function(span) {
		// alert(a);
		train[span.class] = span.content.trim();
		if (span.span) {
			train[span.span.class] = span.span.content.trim();
		}
	});
	return train;
}

function getStations(myjson) {
	var result = [];
	var stationsJson = myjson.query.results.li;

	stationsJson.forEach(function(stationJson) {
		result.push(getStation(stationJson));
	});
	return result;
}

function getStation(stationJson) {
	var station = {};
	stationJson.span.forEach(function(span) {
		if (span.span) {
			station[span.span.class] = span.span.content.trim();
			station[span.class] = "Late";
		} else
			station[span.class] = span.content.trim();

	});
	return station;
}
