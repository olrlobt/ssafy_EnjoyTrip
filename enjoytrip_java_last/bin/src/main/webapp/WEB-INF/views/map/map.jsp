<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>여행 떠나기</title>
    <link rel="icon" href="${root}/assets/img/favicon.png">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${root}/assets/css/bootstrap.min.css">
    <!-- animate CSS -->
    <link rel="stylesheet" href="${root}/assets/css/animate.css">
    <!-- owl carousel CSS -->
    <link rel="stylesheet" href="${root}/assets/css/owl.carousel.min.css">
    <!-- themify CSS -->
    <!-- magnific CSS -->
    <link rel="stylesheet" href="${root}/assets/css/magnific-popup.css">
    <link rel="stylesheet" href="${root}/assets/css/gijgo.min.css">
    <!-- niceselect CSS -->
    <link rel="stylesheet" href="${root}/assets/css/nice-select.css">
    <!-- slick CSS -->
    <link rel="stylesheet" href="${root}/assets/css/slick.css">
    <!-- style CSS -->
    <link rel="stylesheet" href="${root}/assets/css/style.css">
    <link rel="stylesheet" href="${root}/assets/css/map.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        .area {
            position: absolute;
            background: #fff;
            border: 1px solid #888;
            border-radius: 3px;
            font-size: 12px;
            top: -5px;
            left: 15px;
            padding: 2px;
        }

        .info {
            font-size: 12px;
            padding: 5px;
        }

        .info .title {
            font-weight: bold;
        }

        .button-group {
            position: absolute;
            bottom: 10px;
            gap: 10px; /* 버튼 사이의 간격 */
        }


        .route-button {
            flex: 1; /* 모든 버튼이 동일한 공간을 차지하도록 설정 */
        }

        .beautiful-button {
            background-color: #4CAF50; /* Green */
            border: none;
            color: white;
            padding: 12px 28px; /* padding 값 조절 */
            text-align: center;
            text-decoration: none;
            display: block;
            font-size: 14px; /* font-size 조절 */
            margin: 12px 4px;
            transition-duration: 0.4s;
            cursor: pointer;
            border-radius: 12px; /* Rounded corners */
            box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08); /* Shadow effect */
        }


        .beautiful-button:hover {
            background-color: #45a049;
            color: white;
        }

    </style>
</head>

<body>

<%@ include file="../common/header.jsp" %>

<div class="control-container">
    <select id="tourismType">
        <option value="12" selected>관광타입 선택</option>
        <option value="12">관광지</option>
        <option value="14">문화시설</option>
        <option value="15">축제공연행사</option>
        <option value="25">여행코스</option>
        <option value="28">레포츠</option>
        <option value="32">숙박</option>
        <option value="38">쇼핑</option>
        <option value="39">음식점</option>
    </select>
    <div class="search-container">
        <input type="text" class="search-input" id="keyword" placeholder="검색어를 입력하세요...">
        <button class="search-button">검색</button>
    </div>
</div>

<div class="container-map">
    <div class="sidebar">
        <h3>여행 경로</h3>
        <ul id="travelRouteList">
            <!-- 여기에 장소가 추가됩니다 -->
        </ul>

        <div class="button-group">

            <button id="shortRoute_start" class="beautiful-button">시작지 최단경로 보기</button>
            <button id="shortRoute_return" class="beautiful-button">순회 최단경로 보기</button>

            <button id="shortRoute" class="beautiful-button">여행 최단경로 보기</button>
        </div>

        <button id="createRoute" class="route-button">경로 생성</button>
    </div>

    <div id="map" style="width: calc(100% - 250px); height: 1200px;"></div>
</div>


<!-- footer part end-->

<!-- jquery plugins here-->
<script src="${root}/assets/js/jquery-1.12.1.min.js"></script>
<!-- popper js -->
<script src="${root}/assets/js/popper.min.js"></script>
<!-- bootstrap js -->
<script src="${root}/assets/js/bootstrap.min.js"></script>
<!-- magnific js -->
<script src="${root}/assets/js/jquery.magnific-popup.js"></script>
<!-- swiper js -->
<script src="${root}/assets/js/owl.carousel.min.js"></script>
<!-- masonry js -->
<script src="${root}/assets/js/masonry.pkgd.js"></script>
<!-- masonry js -->
<script src="${root}/assets/js/jquery.nice-select.min.js"></script>
<script src="${root}/assets/js/gijgo.min.js"></script>
<!-- contact js -->
<script src="${root}/assets/js/jquery.ajaxchimp.min.js"></script>
<script src="${root}/assets/js/jquery.form.js"></script>
<script src="${root}/assets/js/jquery.validate.min.js"></script>
<script src="${root}/assets/js/mail-script.js"></script>
<script src="${root}/assets/js/contact.js"></script>
<!-- custom js -->
<script src="${root}/assets/js/custom.js"></script>
<script type="text/javascript" src="${root}/assets/js/join.js"></script>
<script type="text/javascript" src="${root}/assets/js/member.js"></script>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"
></script>
<script type="text/javascript" src="/${root}/assets/js/trip.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4c71c6b35a97dd4daf0d4904b4a37b7b
"
></script>
<script type="text/javascript">


    let selectedTourismType = "";  // 이 변수에 선택된 관광타입 ID를 저장합니다.

    const tourismTypeSelect = document.getElementById("tourismType");

    tourismTypeSelect.addEventListener("change", function () {
        selectedTourismType = this.value;  // 선택된 옵션의 value 값을 가져와 변수에 저장합니다.
    });


    var areas = [];

    $.ajaxSetup({
        async: false
    });


    let mapContainer = document.querySelector("#map"), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
            level: 12 // 지도의 확대 레벨
        };

    let map = new kakao.maps.Map(mapContainer, mapOption),
        customOverlay = new kakao.maps.CustomOverlay({})

    let detailMode = false;
    let level = '';
    let polygons = [];
    let infowindow = new kakao.maps.InfoWindow();  // 전역적으로 인포윈도우 하나만 생성

    init("${root}/assets/json/sido.json")

    kakao.maps.event.addListener(map, 'zoom_changed', function () {
        level = map.getLevel()
        if (!detailMode && level <= 10) {
            detailMode = true;
            removePolygon();
            init("${root}/assets/json/sig.json")
        } else if (detailMode && level > 10) {
            detailMode = false;
            removePolygon();
            init("${root}/assets/json/sido.json")
        }
    });


    function removePolygon() {
        for (let i = 0; i < polygons.length; i++) {
            polygons[i].setMap(null);
        }
        areas = [];
        polygons = [];
    }


    function init(path) {

        $.getJSON(path, function (geojson) {
            var units = geojson.features; // 파일에서 key값이 "features"인 것의 value를 통으로 가져옴(이것은 여러지역에 대한 정보를 모두 담고있음)

            $.each(units, function (index, unit) { // 1개 지역씩 꺼내서 사용함. val은 그 1개 지역에 대한 정보를 담음
                var coordinates = []; //좌표 저장할 배열
                var name = ''; // 지역 이름
                var cd_location = '';
                coordinates = unit.geometry.coordinates; // 1개 지역의 영역을 구성하는 도형의 모든 좌표 배열을 가져옴
                name = unit.properties.SIG_KOR_NM; // 1개 지역의 이름을 가져옴
                cd_location = unit.properties.SIG_CD;


                var ob = new Object();
                ob.name = name;
                ob.path = [];
                ob.location = cd_location;
                $.each(coordinates[0], function (index, coordinate) { // []로 한번 더 감싸져 있어서 index 0번의 것을 꺼내야 배열을 접근가능.
                    ob.path
                        .push(new kakao.maps.LatLng(coordinate[1],
                            coordinate[0]));
                });

                areas[index] = ob;
            });//each
        });//getJSON

// 지도에 영역데이터를 폴리곤으로 표시합니다
        for (var i = 0, len = areas.length; i < len; i++) {
            displayArea(areas[i]);
        }

        function displayArea(area) {

            var polygon = new kakao.maps.Polygon({
                map: map, // 다각형을 표시할 지도 객체
                path: area.path,
                strokeWeight: 2,
                strokeColor: '#004c80',
                strokeOpacity: 0.8,
                fillColor: '#fff',
                fillOpacity: 0.7
            });
            polygons.push(polygon);

            kakao.maps.event.addListener(polygon, 'mouseover', function (mouseEvent) {
                polygon.setOptions({fillColor: '#09f'});

                customOverlay.setContent('<div class="area">' + area.name + '</div>');

                customOverlay.setPosition(mouseEvent.latLng);
                customOverlay.setMap(map);
            });

            kakao.maps.event.addListener(polygon, 'mousemove', function (mouseEvent) {

                customOverlay.setPosition(mouseEvent.latLng);
            });

            kakao.maps.event.addListener(polygon, 'mouseout', function () {
                polygon.setOptions({fillColor: '#fff'});
                customOverlay.setMap(null);
            });

            kakao.maps.event.addListener(polygon, 'click', function (mouseEvent) {
                if (!detailMode) {
                    map.setLevel(10); // 레벨을 직접 변경합니다.
                    var latlng = mouseEvent.latLng;

                    // 지도의 중심을 부드럽게 클릭한 위치로 이동시킵니다.
                    map.panTo(latlng);
                } else {

                    callFunctionWithRegionCode(area.location);
                }
            });


        }

    }


    let markers = [];
    let fixedMarkers = [];
    let fixedMarkerPositions = [];
    const API_KEY = "SyDOQnA6DK33e+Cb7RnHCPKxWO84hwK5+uP5UZJ3/S0e0LHI0XVZn1Fd7q3HMwKiG1NI501c55vmkbPNWOjGPg=="
    const keyword = document.querySelector("#keyword");
    const addListenersMap = new Map();
    const removeListenersMap = new Map();


    let debounceTimeout;

    function searchWithDebounce(params, keyword_search) {
        if (debounceTimeout) {
            clearTimeout(debounceTimeout);
        }
        debounceTimeout = setTimeout(() => {
            callAPI(params, keyword_search);
        }, 200);  // 300ms 동안 추가 입력이 없으면 callAPI를 호출합니다.
    }

    keyword.addEventListener("keyup", function () {
        let params = {
            numOfRows: 50,
            MobileOS: 'ETC',
            MobileApp: 'YourAppName',
            keyword: keyword.value,
            contentTypeId: selectedTourismType,
            serviceKey: API_KEY,
            _type: 'json'
        };

        // 여기서 callAPI 대신 searchWithDebounce를 호출합니다.
        searchWithDebounce(params, "searchKeyword1");
    })


    function callAPI(params, keyword_search) {
        for (let i = 0; i < markers.length; i++) {
            if (!fixedMarkers.includes(markers[i])) {
                markers[i].setMap(null);
            }
        }
        // markers = [];  // markers 배열 초기화

        const API_ENDPOINT = 'http://apis.data.go.kr/B551011/KorService1';

        const queryString = new URLSearchParams(params).toString();
        const requestURL = API_ENDPOINT + '/' + keyword_search + '?' + queryString;

        fetch(requestURL)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                let coordinates = getMapCoordinates(data);

                updateMap(coordinates)
            })
            .catch(error => {
            });

        function getMapCoordinates(data) {
            let coordinates = [];

            for (let item of data.response.body.items.item) {
                coordinates.push({
                    mapx: item.mapx,
                    mapy: item.mapy,
                    firstimage: item.firstimage,
                    addr1: item.addr1,
                    areacode: item.areacode,
                    title: item.title  // 제목도 함께 반환하도록 추가 (선택적)
                });
            }
            return coordinates;
        }


        function updateMap(coordinates) {
            infowindow.close();
            for (let coord of coordinates) {
                let markerPosition = new kakao.maps.LatLng(coord.mapy, coord.mapx);

                let isDuplicate = fixedMarkerPositions.some(fixedPosition => {
                    return arePositionsClose(fixedPosition, markerPosition);
                });

                if (isDuplicate) continue;

                let marker = new kakao.maps.Marker({
                    position: markerPosition
                });

                marker.setMap(map);
                markers.push(marker);

                kakao.maps.event.addListener(marker, 'click', function () {
                    handleMarkerClick(marker, coord);
                });
            }
        }


        function handleAddButtonClick(marker, coord) {
            fixMarker(marker, coord);
            addToTravelPlan(coord);
        }

        function addToTravelPlan(coord) {
            const travelList = document.getElementById('travelRouteList');
            // 리스트 아이템 생성
            const listItem = document.createElement('li');
            listItem.textContent = coord.title + `-` + coord.addr1;


            listItem.draggable = true;  // 드래그 가능하게 설정

            // 드래그 시작 이벤트 처리
            listItem.addEventListener('dragstart', (e) => {
                e.dataTransfer.setData('text/plain', listItem.textContent);
                e.dataTransfer.dropEffect = 'move';
                e.target.classList.add('dragging');
            });

            // 드래그 종료 이벤트 처리
            listItem.addEventListener('dragend', (e) => {
                e.target.classList.remove('dragging');
            });


            // 삭제 버튼 추가 (선택적)
            const removeButton = document.createElement('button');
            removeButton.textContent = '삭제';
            removeButton.classList.add("deleteButton");
            removeButton.addEventListener('click', () => {
                travelList.removeChild(listItem);
            });
            listItem.appendChild(removeButton);
            listItem.dataset.lat = coord.mapy; // 위도
            listItem.dataset.lng = coord.mapx; // 경도
            listItem.dataset.title = coord.title;
            travelList.appendChild(listItem);
        }


        function handleRemoveButtonClick(marker) {
            revertMarker(marker);
        }


        function handleMarkerClick(marker, coord) {
            const isFixed = fixedMarkers.includes(marker);

            // 인포윈도우 업데이트 로직
            const content = generateInfoWindowContent(coord, isFixed);
            infowindow.setContent(content);
            infowindow.open(map, marker);

            // 버튼 및 이벤트 처리 로직
            if (isFixed) {
                const removeButton = document.querySelector('.remove-marker-btn');
                if (removeListenersMap.has(marker)) {
                    removeButton.removeEventListener('click', removeListenersMap.get(marker));
                    removeListenersMap.delete(marker);
                }

                const removeListener = () => handleRemoveButtonClick(marker);
                removeListenersMap.set(marker, removeListener);
                removeButton.addEventListener('click', removeListener);
            } else {
                const addButton = document.querySelector('.add-marker-btn');

                if (addListenersMap.has(marker)) {
                    addButton.removeEventListener('click', addListenersMap.get(marker));
                    addListenersMap.delete(marker);
                }

                const addListener = () => handleAddButtonClick(marker, coord);
                addListenersMap.set(marker, addListener);
                addButton.addEventListener('click', addListener);
            }


            document.querySelector('.register-btn').addEventListener('click', function () {
                showSweetAlert(coord);
            });

        }


        function revertMarker(targetMarker) {
            const markerTitle = targetMarker.getTitle();
            const listItemToRemove = document.querySelector(`li[data-title=" ` + markerTitle + `"]`);


            if (listItemToRemove) {
                removeFromTravelRoute(listItemToRemove);
            }

            const markerIndex = fixedMarkers.indexOf(targetMarker);
            if (markerIndex > -1) {
                fixedMarkers.splice(markerIndex, 1); // fixedMarkers 배열에서 제거
            }

            const positionIndex = fixedMarkerPositions.findIndex(pos => arePositionsClose(pos, targetMarker.getPosition()));
            if (positionIndex > -1) {
                fixedMarkerPositions.splice(positionIndex, 1);
            }

            targetMarker.setImage(null);

            // 이벤트 핸들러를 변수로 저장
            const addListener = () => handleAddButtonClick(targetMarker, coord);

            // 이벤트 핸들러를 등록하고 제거할 때 동일한 함수를 사용
            const addButton = document.querySelector('.add-marker-btn');

            if (addListenersMap.has(targetMarker)) {
                addListenersMap.delete(targetMarker);
            }

            if (addListener != null && addButton != null) {

                addListenersMap.set(targetMarker, addListener);
                addButton.addEventListener('click', addListener);

            }
            drawLine();
            infowindow.close();
        }

        function removeFromTravelRoute(listItem) {
            listItem.remove();
        }


        function fixMarker(marker, coord) {
            marker.setTitle(coord.title);
            fixedMarkers.push(marker);
            console.log(fixedMarkers);
            fixedMarkerPositions.push(marker.getPosition());

            const redMarkerImage = new kakao.maps.MarkerImage(
                '${root}/assets/img/pickMarker.png',
                new kakao.maps.Size(40, 40)
            );

            marker.setImage(redMarkerImage);
            handleMarkerClick(marker, coord);  // Update the infowindow content
        }

        function generateInfoWindowContent(coord, isFixed) {
            const buttonLabel = isFixed ? '삭제' : '추가';
            const buttonClass = isFixed ? 'remove-marker-btn' : 'add-marker-btn';

            return `
        <div style="display: flex; flex-direction: column; padding: 5px; align-items: start; max-width: 250px;">
            <div style="display: flex; align-items: center;">
                <img src= "` + coord.firstimage + `" alt="` + coord.title + `" style="width: 60px; height: 60px; object-fit: cover; margin-right: 10px;"/>
                <div style="font-size: 0.9rem;">
                    <strong>` + coord.title + `</strong><br/>
                    ` + coord.addr1 + `
                </div>
            </div>
            <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 5px;">
                <button class="button-common ` + buttonClass + `">` + buttonLabel + `  </button>
                <button class="button-common register-btn">등록</button>
            </div>
        </div>
    `;
        }


        function arePositionsClose(position1, position2, threshold = 0.00001) {
            const diffLat = Math.abs(position1.La - position2.La);
            const diffLng = Math.abs(position1.Ma - position2.Ma);
            return diffLat < threshold && diffLng < threshold;
        }


        document.getElementById('travelRouteList').addEventListener('click', function (event) {
            if (event.target.className === 'deleteButton') {  // 'deleteButton' 클래스를 가진 버튼을 클릭했을 때만 동작
                const listItem = event.target.closest('li');   // 삭제 버튼의 부모 li 요소를 찾습니다.
                const markerTitle = listItem.getAttribute('data-title');

                // fixedMarkers 배열에서 해당 제목과 일치하는 마커를 찾습니다.
                const targetMarker = fixedMarkers.find(marker => marker.getTitle() === markerTitle);
                if (targetMarker) {
                    revertMarker(targetMarker); // 해당 마커의 고정을 해제합니다.
                }

                // 목록에서 해당 항목을 제거합니다.
                listItem.remove();
            }
        });
    }

    // 아이템을 드래그 시작할 때의 이벤트 핸들러
    let draggedItem = null; // 드래그 중인 요소를 저장할 변수

    document.getElementById('travelRouteList').addEventListener('dragstart', (e) => {
        e.dataTransfer.setData('text/html', e.target.outerHTML);
        draggedItem = e.target; // 드래그 중인 요소를 변수에 저장
    });

    document.getElementById('travelRouteList').addEventListener('dragover', (e) => {
        e.preventDefault();
    });

    document.getElementById('travelRouteList').addEventListener('drop', (e) => {
        e.preventDefault();

        if (e.target.tagName === "LI") { // 드랍 대상이 LI 요소인지 확인합니다.
            // 드롭 위치에 원래 요소를 놓는다.
            const droppedHTML = e.dataTransfer.getData('text/html');
            const newElement = new DOMParser().parseFromString(droppedHTML, 'text/html').body.firstChild;
            e.target.insertAdjacentElement('beforebegin', newElement); // LI 요소 앞에 삽입

            // 원래 드래그된 요소를 제거한다.
            if (draggedItem) {
                draggedItem.remove();
                draggedItem = null; // 임시 저장 변수 초기화
            }

            drawLine(); // 위치가 변경된 후 경로를 다시 그린다.
        }
    });


    //순환 최단경로 얻기
    document.getElementById('shortRoute_return').addEventListener('click', () => {

        const lis = Array.from(document.querySelectorAll('#travelRouteList li'));
        const sortedLis = nearestNeighbor(lis);
        const ul = document.querySelector('#travelRouteList');
        sortedLis.forEach(li => ul.appendChild(li));

        drawLine();
    })

    function nearestNeighbor(lis) {
        let unvisited = [...lis];
        let current = unvisited.shift();
        let tour = [current];

        while (unvisited.length > 0) {
            let nearest = unvisited.reduce((minLi, li) => {
                const d1 = getDistance(
                    parseFloat(current.getAttribute('data-lat')),
                    parseFloat(current.getAttribute('data-lng')),
                    parseFloat(li.getAttribute('data-lat')),
                    parseFloat(li.getAttribute('data-lng'))
                );
                const d2 = minLi ? getDistance(
                    parseFloat(current.getAttribute('data-lat')),
                    parseFloat(current.getAttribute('data-lng')),
                    parseFloat(minLi.getAttribute('data-lat')),
                    parseFloat(minLi.getAttribute('data-lng'))
                ) : Number.POSITIVE_INFINITY;
                return d1 < d2 ? li : minLi;
            }, null);

            current = nearest;
            unvisited = unvisited.filter(li => li !== nearest);
            tour.push(current);
        }

        return tour;
    }


    //시작지로부터 최단경로 얻기
    document.getElementById('shortRoute_start').addEventListener('click', () => {

        const lis = Array.from(document.querySelectorAll('#travelRouteList li'));

        const baseLat = lis[0].getAttribute('data-lat');
        const baseLon = lis[0].getAttribute('data-lng');

        lis.sort((a, b) => {
            const aLat = parseFloat(a.getAttribute('data-lat'));
            const aLon = parseFloat(a.getAttribute('data-lng'));
            const bLat = parseFloat(b.getAttribute('data-lat'));
            const bLon = parseFloat(b.getAttribute('data-lng'));

            const distanceA = getDistance(baseLat, baseLon, aLat, aLon);
            const distanceB = getDistance(baseLat, baseLon, bLat, bLon);

            return distanceA - distanceB;
        });

// 정렬된 li 태그들을 DOM에 다시 삽입
        const ul = document.querySelector('#travelRouteList'); // 상위 ul 또는 ol 요소
        lis.forEach(li => ul.appendChild(li));
        drawLine();
    })


    //모든 경로중 최단경로 얻기
    document.getElementById('shortRoute').addEventListener('click', () => {

        const lis = Array.from(document.querySelectorAll('#travelRouteList li'));
        const permutations = permute(lis);

        let shortestPath = null;
        let shortestDistance = Number.POSITIVE_INFINITY;

        permutations.forEach(path => {
            const currentDistance = totalDistance(path);
            if (currentDistance < shortestDistance) {
                shortestPath = path;
                shortestDistance = currentDistance;
            }
        });

        const ul = document.querySelector('#travelRouteList');
        shortestPath.forEach(li => ul.appendChild(li));
        drawLine();

    })

    function totalDistance(path) {
        let distance = 0;
        for (let i = 1; i < path.length; i++) {
            distance += getDistance(
                parseFloat(path[i - 1].getAttribute('data-lat')),
                parseFloat(path[i - 1].getAttribute('data-lng')),
                parseFloat(path[i].getAttribute('data-lat')),
                parseFloat(path[i].getAttribute('data-lng'))
            );
        }
        return distance;
    }

    function permute(array) {
        if (array.length === 0) return [[]];
        const result = [];
        for (let i = 0; i < array.length; i++) {
            const rest = permute(array.slice(0, i).concat(array.slice(i + 1)));
            for (let j = 0; j < rest.length; j++) {
                result.push([array[i]].concat(rest[j]));
            }
        }
        return result;
    }


    function toRadians(degrees) {
        return degrees * Math.PI / 180;
    }

    //하버사인 공식
    function getDistance(lat1, lon1, lat2, lon2) {
        var R = 6371e3; // 지구의 반지름 (m)
        var φ1 = toRadians(lat1);
        var φ2 = toRadians(lat2);
        var Δφ = toRadians(lat2 - lat1);
        var Δλ = toRadians(lon2 - lon1);

        var a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
            Math.cos(φ1) * Math.cos(φ2) *
            Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // 결과는 미터 단위
    }


    let line; // 글로벌 변수로 선 객체를 선언합니다.

    document.getElementById('createRoute').addEventListener('click', drawLine)

    function drawLine() {
        // 기존의 선이 있다면 지우기
        if (line) {
            line.setMap(null);
        }

        // 여행 계획 리스트의 순서대로 좌표를 가져옵니다.
        const travelList = document.getElementById('travelRouteList');
        const items = travelList.querySelectorAll('li');
        const linePath = [];
        items.forEach(item => {
            const lat = parseFloat(item.getAttribute("data-lat"));
            const lng = parseFloat(item.getAttribute("data-lng"));
            linePath.push(new kakao.maps.LatLng(lat, lng));
        });

        const polylineOptions = {
            path: linePath,
            strokeWeight: 3,
            strokeColor: '#db4040',
            strokeOpacity: 0.7,
            strokeStyle: 'solid'
        };

        line = new kakao.maps.Polyline(polylineOptions);
        line.setMap(map);  // map은 카카오 지도 객체입니다.
    }


    // 주어진 번호와 해당 번호에 대응하는 지역 코드를 매핑하는 객체
    const regionCodeMapping = {
        11: 1,  // 서울
        26: 2,  // 인천
        27: 3,  // 대전
        28: 4,  // 대구
        29: 5,  // 광주
        21: 6,  // 부산
        31: 7,  // 울산
        36: 8,  // 세종특별자치시
        41: 31, // 경기도
        42: 32, // 강원특별자치도
        43: 33, // 충청북도
        44: 34, // 충청남도
        47: 35, // 경상북도
        48: 36, // 경상남도
        45: 37, // 전라북도
        46: 38, // 전라남도
        50: 39  // 제주도
    };

    function callFunctionWithRegionCode(originalCode) {
        // 매핑 객체를 사용해 지역 코드를 찾는다.
        let code = String(originalCode).slice(0, 2)
        const regionCode = regionCodeMapping[code];
        if (regionCode) {

            let params = {
                numOfRows: 60,
                MobileOS: 'ETC',
                MobileApp: 'YourAppName',
                contentTypeId: selectedTourismType,
                serviceKey: API_KEY,
                areaCode: regionCode,
                _type: 'json'
            };

            // 여기서 callAPI 대신 searchWithDebounce를 호출합니다.
            callAPI(params, "areaBasedList1");  // 해당 코드를 함수에 전달
        } else {
            console.error("Invalid original code provided");
        }
    }


    function showSweetAlert(coord) {
        swal.fire({
            title: "핫플레이스 등록",
            html: `
        <form id="article-form" action="${root }/article?action=write&type=${type}" method="post">
          <img src="` + coord.firstimage + `" alt="` + coord.title + `" style="width: 60px; height: 60px; object-fit: cover; margin-right: 10px;"/>
          <strong>` + coord.title + `</strong><br/>
          ` + coord.addr1 + `<br/><br/>
          <label style="display: block; font-size: 0.9rem; margin-bottom: 5px;">다녀온 날짜</label>
          <input type="text" id="input1" class="swal2-input" placeholder="다녀온 날짜" value="` + new Date().toISOString().split('T')[0] + `">
  <label style="display: block; font-size: 0.9rem; margin-bottom: 5px;">제목</label>
          <input type="text" id="subject" name ="title" class="swal2-input" placeholder="제목" value="` + coord.title + `">
          <label style="display: block; font-size: 0.9rem; margin-bottom: 5px;">상세 설명</label>
          <textarea id="input2" class="swal2-textarea" name ="content" placeholder="상세 설명" style="height: 150px;"></textarea>
           <input type="hidden" id="type" name ="type" class="swal2-input"  value="hot">
        </form>
    `,
            showCancelButton: true,
            confirmButtonText: '등록',
            cancelButtonText: '취소',
            reverseButtons: true,
            preConfirm: function () {
                let input1Value = document.getElementById('input1').value;
                let input2Value = document.getElementById('input2').value;
                return [input1Value, input2Value];
            }
        }).then(function (result) {
            if (result.isConfirmed) {

                const form = document.querySelector("#article-form");
                form.submit();

            }
        });
    }

</script>
</body>
</html>