$( document ).ready(function() {
	console.log( "ready!" );

	initMap();
});

// Nous initialisons une liste de marqueurs
var villes = {
	"Paris": { "lat": 44.852969, "lon": 4.850000 },
	"Brest": { "lat": 48.383, "lon": -4.500 },
	"Quimper": { "lat": 48.000, "lon": -4.100 },
	"Bayonne": { "lat": 43.500, "lon": -1.467 }
};
// Fonction d'initialisation de la carte
function initMap() {

    var lat = 45.750000;
    var lon = 4.850000;
    var macarte = null;
	// Créer l'objet "macarte" et l'insèrer dans l'élément HTML qui a l'ID "map"
	maCarte = L.map('map').setView([lat, lon], 11);

	readJsonResult();

	// Leaflet ne récupère pas les cartes (tiles) sur un serveur par défaut. Nous devons lui préciser où nous souhaitons les récupérer. Ici, openstreetmap.fr
	L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
		// Il est toujours bien de laisser le lien vers la source des données
		attribution: 'données © OpenStreetMap/ODbL - rendu OSM France',
		minZoom: 1,
		maxZoom: 20
	}).addTo(maCarte);      	
}

function parcoursJson2(data){
	$.each(data, function (key, val) {
		var trajet = new L.Polyline(pointsArrets(val), {
			color: randomColor(),
			weight: 5,
			smoothFactor: 1
		 });

		 maCarte.addLayer(trajet);
		 $.each( val.trajetList, function(key, val){
			 console.log(val);
			var marker = L.marker([val.departureCoordinate.x, val.departureCoordinate.y]).addTo(maCarte);

			marker.bindPopup("<b>Arrêt numéro ARR"+key+"</b><br>x : "+val.arrivalCoordinate.x+"<br>y : "+val.arrivalCoordinate.y);
		});

	})
}

function parcoursJson(data){
	$.each( data, function( key, val ) {
		console.log("Pour : "+key);

		var trajet = new L.Polyline(pointsArrets(val), {
			color: randomColor(),
			weight: 5,
			smoothFactor: 1
		 });

		maCarte.addLayer(trajet);
		$.each( val, function(key, val){
			var marker = L.marker([val.x, val.y]).addTo(maCarte);
			marker.bindPopup("Arrêt numero : "+key+"</br> x : "+val.x+" y : "+val.y);
			marker.bindPopup("<b>Arrêt numéro "+key+"</b><br>x : "+val.x+"<br>y : "+val.y).openPopup();
		});
		
	  });
}

function randomColor(){
	var color;
	var r = Math.floor(Math.random() * 255);
	var g = Math.floor(Math.random() * 255);
	var b = Math.floor(Math.random() * 255);
	color= "rgb("+r+" ,"+g+","+ b+")"; 
	return color;
}

function pointsArrets(items) {
	var pointsArray = new Array();
	console.log(items);
	$.each(items.trajetList, function(key, val){
		pointsArray.push(new L.LatLng(val.departureCoordinate.x,val.departureCoordinate.y));
		pointsArray.push(new L.LatLng(val.arrivalCoordinate.x,val.arrivalCoordinate.y));
	})
	return pointsArray;
}

function readJsonResult(){	
	$.getJSON('result.json',function(data){
		parcoursJson2(data);
	  });
}

