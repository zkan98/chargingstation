import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
    const [address, setAddress] = useState('');

    const handleChange = (e) => {
        setAddress(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (address) {
            // 네이버 지도 API를 호출하여 주소를 좌표로 변환하고 지도 업데이트
            window.searchAddressToCoordinate(address);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit} style={{ position: 'absolute', top: '10px', left: '10px', zIndex: 10, backgroundColor: 'white', padding: '10px', borderRadius: '5px' }}>
                <div>
                    아이디: <br/><input type="text" name="id" />
                </div>
                <div>
                    비밀번호: <br/><input type="password" name="pwd" />
                </div>
                <div>
                    주소: <br/><input type="text" name="address" value={address} onChange={handleChange} />
                </div>
                <button type="submit">주소 검색</button>
            </form>
            <div id="map" style={{ width: '100%', height: '700px' }}></div>
        </div>
    );
}

export default App;