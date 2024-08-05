import React, { useEffect, useRef } from 'react';

const loadScript = (url) => {
    return new Promise((resolve, reject) => {
        const script = document.createElement('script');
        script.src = url;
        script.async = true;
        script.onload = resolve;
        script.onerror = reject;
        document.head.appendChild(script);
    });
};

const NaverMap = () => {
    const mapElement = useRef(null);

    useEffect(() => {
        const initMap = () => {
            const { naver } = window;
            if (!mapElement.current || !naver) return;

            const map = new naver.maps.Map(mapElement.current, {
                center: new naver.maps.LatLng(37.3595704, 127.105399),
                zoom: 15,
            });

            const marker = new naver.maps.Marker({
                position: new naver.maps.LatLng(37.3595704, 127.105399),
                map: map,
            });

            const infoWindow = new naver.maps.InfoWindow({
                anchorSkew: true,
            });

            const searchCoordinateToAddress = (latlng) => {
                infoWindow.close();
                naver.maps.Service.reverseGeocode(
                    {
                        coords: latlng,
                        orders: [
                            naver.maps.Service.OrderType.ADDR,
                            naver.maps.Service.OrderType.ROAD_ADDR,
                        ].join(','),
                    },
                    (status, response) => {
                        if (status !== naver.maps.Service.Status.OK) {
                            return alert('Something Wrong!');
                        }

                        const items = response.v2.results,
                            address = items.length ? items[0].address.name : '';
                        infoWindow.setContent(
                            `<div style="padding:10px;min-width:200px;line-height:150%;">${address}</div>`
                        );
                        infoWindow.open(map, latlng);
                    }
                );
            };

            const searchAddressToCoordinate = (address) => {
                naver.maps.Service.geocode(
                    {
                        query: address,
                    },
                    (status, response) => {
                        if (status !== naver.maps.Service.Status.OK) {
                            return alert('Something Wrong!');
                        }

                        const result = response.v2.addresses[0];
                        const point = new naver.maps.Point(result.x, result.y);

                        map.setCenter(point);
                        marker.setPosition(point);
                        searchCoordinateToAddress(point);
                    }
                );
            };

            document
                .getElementById('submit')
                .addEventListener('click', (e) => {
                    e.preventDefault();
                    const address = document.getElementById('name').value;
                    searchAddressToCoordinate(address);
                });

            map.addListener('click', (e) => {
                marker.setPosition(e.coord);
                searchCoordinateToAddress(e.coord);
            });
        };

        loadScript('https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=avh0jngjl9&submodules=geocoder')
            .then(initMap)
            .catch((error) => {
                console.error('Naver Maps script load error:', error);
            });
    }, []);

    return (
        <>
            <div id="address-form">
                <label htmlFor="name">도로명 주소 입력:</label>
                <input type="search" id="name" name="name" required size="10" />
                <button id="submit">검색</button>
            </div>
            <div id="map" ref={mapElement} style={{ width: '100%', height: '700px' }}></div>
        </>
    );
};

export default NaverMap;
