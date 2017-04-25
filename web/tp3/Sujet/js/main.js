'use strict';

// Pas besoin d'évenement window.onload puisqu'on utilise l'attribut defer
// lorsque l'on charge notre script

var genres;
console.log('log');

if (self.fetch) {
    fetch('http://127.0.0.1:3000/genres')
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
        })
        .then(function (data) {
            var select = document.querySelector('select');
            var option;
            genres = data;
            data.forEach(function (element) {
                option = document.createElement('option');
                option.innerHTML = element.name;
                option.setAttribute('value', element.id);
                select.add(option);
            });
            select.addEventListener('change', (element) => {
                loadArtists(element.target.value);
            }, true);
            loadArtists(select.selectedOptions[0].value);
        })
        .catch(function (error) {
            console.log(error);
            return [];
        });
}


function loadArtists(genre) {
    var h2 = document.querySelector('#main h2');
    var p = document.querySelector('#main > p');
    /*Mise à jour du titre*/
    h2.textContent = 'Top ' + genre + ' artists';
    /*Mise à jour de la description*/
    p.textContent = genres.find((element) => element.id === genre).description;
    /*Remplissage de la liste*/
    fetch('http://127.0.0.1:3000/genre/' + genre + '/artists')
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
        })
        .then(function (data) {
            var ul = document.querySelector('#main > ul');
            var li, h3, a, img;

            ul.innerHTML = '';
            data.forEach(function (element) {
                li = document.createElement('li');
                a = document.createElement('a');
                h3 = document.createElement('h3');
                img = document.createElement('img');

                h3.textContent = element.name;
                img.setAttribute('src', element.photo);
                a.setAttribute('href', '#');

                a.setAttribute('id', element.id);
                a.addEventListener('click', artistSelected, true);

                a.appendChild(h3);
                li.appendChild(a);
                li.appendChild(img);
                ul.appendChild(li);
            });

        });
}

function artistSelected(event) {
    var id = event.target.parentElement.id;
    fetch('http://127.0.0.1:3000/artist/' + id + '/albums')
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
        })
        .then(function (data) {
            var aside = document.querySelector('#albums');
            var buttonOk = document.querySelector('#albums form button');
            var table = document.querySelector('#albums table');
            var tableBody = document.querySelector('#albums table tbody');
            var tableHead = document.querySelector('#albums h2');
            var row, cover, title, year, label, coverImg, titleText, yearText, labelText;

            table.style.width = '100%';

            tableHead.style.backgroundColor = 'aqua';
            tableHead.style.color = 'white';
            tableHead.style.marginTop = '0';

            tableBody.innerHTML = '';

            data.forEach(function (element) {
                row = document.createElement('tr');
                cover = document.createElement('td');
                title = document.createElement('td');
                year = document.createElement('td');
                label = document.createElement('td');

                coverImg = document.createElement('img');
                coverImg.setAttribute('src', element.cover);
                coverImg.setAttribute('width', 60);
                coverImg.setAttribute('height', 60);


                titleText = document.createElement('h5');
                titleText.textContent = element.title;

                yearText = document.createElement('h5');
                yearText.textContent = element.year;

                labelText = document.createElement('h5');
                labelText.textContent = element.label;


                cover.appendChild(coverImg);
                title.appendChild(titleText);
                year.appendChild(yearText);
                label.appendChild(labelText);

                row.appendChild(cover);
                row.appendChild(title);
                row.appendChild(year);
                row.appendChild(label);
                tableBody.appendChild(row);
                row.style.width = '100%';

            });

            aside.style.visibility = 'visible';
            aside.style.opacity = '1.0';
            aside.style.transition = 'opacity 1s ease';
            aside.style.position = 'fixed';
            aside.style.top = 'calc(50% - ' + aside.clientHeight / 2 + 'px)';
            aside.style.left = 'calc(50% - ' + aside.clientWidth / 2 + 'px)';

            buttonOk.addEventListener('click', () => {
                aside.style.opacity = '0';

                /*On le remet en hidden après la transition*/
                setTimeout(function () {
                    aside.style.visibility = 'hidden';
                }, 1000);


            }, true);

        });
}
