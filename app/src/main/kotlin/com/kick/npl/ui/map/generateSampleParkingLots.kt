package com.kick.npl.ui.map

import com.kick.npl.model.ParkingLotData
import com.kick.npl.model.ParkingLotType
import com.naver.maps.geometry.LatLng

fun generateSampleParkingLots(): List<ParkingLotData> = buildList {
    /*
    ParkingLotData(
        id = "1",
        name = "분당구청 주차장",
        address = "경기도 성남시 분당구 정자동 206",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.358315, 127.114454),
        favorite = false,
        pricePer10min = 500,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "2",
        name = "수지구청 주차장",
        address = "경기도 성남시 수정구 태평동 3377",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.443947, 127.137157),
        favorite = true,
        pricePer10min = 1000,
        parkingLotType = ParkingLotType.TYPE_B
    ).let { add(it) }

    ParkingLotData(
        id = "3",
        name = "중원구청 주차장",
        address = "경기도 성남시 중원구 상대원동 3971",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.437705, 127.154780),
        favorite = true,
        pricePer10min = 2000,
        parkingLotType = ParkingLotType.TYPE_C
    ).let { add(it) }

    ParkingLotData(
        id = "4",
        name = "성남시청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.419662, 127.126939),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "5",
        name = "광명시청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.477496, 126.866642),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "6",
        name = "광명구청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.480601, 126.852971),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "7",
        name = "광명군청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.465027, 126.854992),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "8",
        name = "종로구청 주차장",
        address = "서울특별시 종로구 종로",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.5730, 126.9784),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "9",
        name = "중구청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.5635, 126.9975),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "11",
        name = "용산구청 주차장",
        address = "서울시 용산구 후암동 111-1",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.5311, 126.9810),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "12",
        name = "성동구청 주차장",
        address = "서울시 성동구 성수동1가 656-1",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.5635, 127.0366),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "13",
        name = "광진구청 주차장",
        address = "서울시 광진구 능동로 1",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.5382, 127.0835),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "14",
        name = "동대문 주차장",
        address = "서울시 동대문구 천호대로 145",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.5744, 127.0395),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "15",
        name = "중량구 주차장",
        address = "서울시 중랑구 신내로 24길 10",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.6053, 127.0930),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "16",
        name = "성북구청 주차장",
        address = "서울시 성북구 보문로 168",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.5893, 127.0165),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "17",
        name = "강북구청 주차장",
        address = "서울시 강북구 도봉로 384",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.6396, 127.0256),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "18",
        name = "도봉구청 주차장",
        address = "서울시 도봉구 도봉로 552",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.6659, 127.0318),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "19",
        name = "노원구청 주차장",
        address = "서울시 노원구 노원로 283",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(37.6659, 127.0318),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }
*/

    ParkingLotData(
        id = "21",
        name = "금정성당 교인 주차장",
        address = "부산광역시 금정구 수림로45번길 36",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(35.23830376536701, 129.08709531367415),
        favorite = false,
        pricePer10min = 2500,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "22",
        name = "금정구청 주차장",
        address = "부산광역시 금정구 중앙대로 1777",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(35.24293340199663, 129.09244256932067),
        favorite = false,
        pricePer10min = 1100,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "23",
        name = "구서쌍용예가 상가주차장",
        address = "부산광역시 금정구 금샘로 261",
        addressDetail = "lacus",
        imageUri = "",
        latLng = LatLng(35.247025165472266, 129.0848417662993),
        favorite = false,
        pricePer10min = 1800,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "24",
        name = "금강식물원 옆 주차장",
        address = "부산광역시 동래구 우장춘로 221",
        addressDetail = "lacus",
        imageUri = "https://i.imgur.com/eI9Bxrs.png",
        latLng = LatLng(35.22613468546215, 129.0770917515041),
        favorite = false,
        pricePer10min = 500,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "25",
        name = "온천장 홈플러스 주차장",
        address = "서울시 강서구 화곡동 980-1",
        addressDetail = "lacus",
        imageUri = "https://i.imgur.com/d8vENZ5.png",
        latLng = LatLng(35.22169377352264, 129.08589559088776),
        favorite = false,
        pricePer10min = 1000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "26",
        name = "금정사 주차 공터",
        address = "부산광역시 동래구 우장춘로 157-59",
        addressDetail = "lacus",
        imageUri = "https://i.imgur.com/jKicjGy.png",
        latLng = LatLng(35.221677221892136, 129.07427665102537),
        favorite = false,
        pricePer10min = 300,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "27",
        name = "금양중 운동장",
        address = "부산광역시 금정구 기찰로22번길 37",
        addressDetail = "lacus",
        imageUri = "https://i.imgur.com/Kk6uPLS.png",
        latLng = LatLng(35.23369378277004, 129.09547266797335),
        favorite = false,
        pricePer10min = 1500,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "28",
        name = "가마실공원 주차장",
        address = "부산 금정구 부곡동 296-2",
        addressDetail = "lacus",
        imageUri = "https://i.imgur.com/JRI9Aep.png",
        latLng = LatLng(35.22802018494119, 129.09187824460273),
        favorite = false,
        pricePer10min = 2000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "29",
        name = "럭키아파트 110-2단지 옆",
        address = "부산광역시 동래구 충렬대로107번길 54",
        addressDetail = "lacus",
        imageUri = "https://i.imgur.com/pdAjh7x.png",
        latLng = LatLng(35.20852729262678, 129.07545174513217),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "30",
        name = "석불사 신자 주차장",
        address = "부산광역시 북구 만덕고개길 143-96",
        addressDetail = "lacus",
        imageUri = "https://i.imgur.com/Li7C9KI.png",
        latLng = LatLng(35.22159809871139, 129.04876983152315),
        favorite = false,
        pricePer10min = 800,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "31",
        name = "탑마트 법정점 주차장",
        address = "부산광역시 금정구 공단서로 22",
        addressDetail = "lacus",
        imageUri = "https://i.imgur.com/bg2Uszq.png",
        latLng = LatLng(35.217056891642514, 129.1111263557927),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "32",
        name = "부산가톨릭대학교 방송국 주차장",
        address = "부산광역시 금정구 오륜대로 57",
        addressDetail = "lacus",
        imageUri = "https://i.imgur.com/6tHCxgX.png",
        latLng = LatLng(35.24529709117954, 129.0982703047769),
        favorite = false,
        pricePer10min = 1000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "33",
        name = "부산대학교 새벽벌 도서관 주차장",
        address = "부산광역시 금정구 부산대학로63번길 15",
        addressDetail = "lacus",
        imageUri = "https://i.imgur.com/7VqlNbN.png",
        latLng = LatLng(35.23574428670318, 129.08135401107754),
        favorite = false,
        pricePer10min = 2000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "34",
        name = "부산대학교 약학관 주차장",
        address = "부산광역시 금정구 부산대학로63번길 2",
        addressDetail = "테스트",
        imageUri = "https://i.imgur.com/FdiSIE1.png",
        latLng = LatLng(35.23226325180836, 129.07836005038558),
        favorite = false,
        pricePer10min = 800,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "35",
        name = "장전동 부산은행 주차장",
        address = "부산광역시 금정구 금정로 75",
        addressDetail = "테스트",
        imageUri = "https://i.imgur.com/13E3rrc.png",
        latLng = LatLng(35.23140932336079, 129.08635028228423),
        favorite = false,
        pricePer10min = 2300,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "36",
        name = "장전제일교회 옆 공터",
        address = "부산광역시 금정구 금정로 50",
        addressDetail = "테스트",
        imageUri = "https://i.imgur.com/Y6i5tuH.png",
        latLng = LatLng(35.22902874093737, 129.08644032167942),
        favorite = false,
        pricePer10min = 1800,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "37",
        name = "GS 금정 아파트 놀이터",
        address = "부산광역시 금정구 금강로 225",
        addressDetail = "테스트",
        imageUri = "https://i.imgur.com/xFXiJbz.png",
        latLng = LatLng(35.22803875470893, 129.08296648302607),
        favorite = false,
        pricePer10min = 2200,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "38",
        name = "부산대 진리관 가동 주차장",
        address = "부산광역시 금정구 부산대학로63번길 2",
        addressDetail = "테스트",
        imageUri = "https://i.imgur.com/pqfXuG4.png",
        latLng = LatLng(35.23806608324525, 129.07786832946474),
        favorite = false,
        pricePer10min = 1000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "39",
        name = "다이소 부산대역점 주차장",
        address = "부산광역시 금정구 장전온천천로 55",
        addressDetail = "테스트",
        imageUri = "https://i.imgur.com/emYkxHU.jpg",
        latLng = LatLng(35.229856034720484, 129.08902321865324),
        favorite = false,
        pricePer10min = 2000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "40",
        name = "맥도날드 장전점 주차장",
        address = "부산광역시 금정구 금정로60번길 24",
        addressDetail = "테스트",
        imageUri = "https://i.imgur.com/YQMPcbS.png",
        latLng = LatLng(35.22951047982731, 129.08750699219462),
        favorite = false,
        pricePer10min = 2000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }
}
