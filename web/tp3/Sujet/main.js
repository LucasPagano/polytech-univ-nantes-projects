'use strict';

console.log("log");
var genres;

if (self.fetch){
	fetch('http://127.0.0.1:3000/genres')
	.then(function (response) {
		if (response.ok){
			return response.json();
		}
	})
	.then(function (data){
		var select = document.querySelector('select');
		var option;
		genres = data;
		data.forEach(function(element){
			option = document.createElement('option');
			option.innerHTML = element.name;
			option.setAttribute('value', element.id);
			select.add(option);
		});
		select.addEventListener('change', (element) => {loadArtists(element.target.value);}, true);
		loadArtists(select.selectedOptions[0].value);
	})
	.catch(function (error){
		console.log(error);
		return [];
	})
}

function loadArtists(genre){
	var h2 = document.querySelector('#main h2');
	var p = document.querySelector('#main > p');
	/*Mise à jour du titre*/
	h2.textContent = 'Top ' + genre + ' artists';
	/*Mise à jour de la description*/
	p.textContent = genres.find((element)=>element.id === genre).description;
	/*Remplissage de la liste*/
	fetch('http://127.0.0.1:3000/genre/' + genre + '/artists')
	.then(function (response) {
		if (response.ok){
			return response.json();
		}
	})
	.then(function (data){
		var ul = document.querySelector('#main > ul');
		var li, h3, a, img;

		ul.innerHTML = '';
		data.forEach(function(element){
			li = document.createElement('li');
			a = document.createElement('a');
			h3 = document.createElement('h3');
			img = document.createElement('img');

			h3.textContent = element.name;
			img.setAttribute('src', element.photo);
			a.setAttribute('href', '#');

			a.appendChild(h3)
			li.appendChild(a);
			li.appendChild(img);
			ul.appendChild(li);
		});

	});
}

function artistSelected (evt){
	
}
