package me.f1c.domain.driver

enum class DriverInfo(
    val driverId: String,
    val koreanDriverName: String,
    val headshotUrl: String,
) {
    ALBON(
        "albon",
        "알렉산더 알본",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/A/ALEALB01_Alexander_Albon/alealb01.png",
    ),
    ALONSO(
        "alonso",
        "페르난도 알론소",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/F/FERALO01_Fernando_Alonso/feralo01.png",
    ),
    BEARMAN(
        "bearman",
        "올리버 베어먼",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/O/OLIBEA01_Oliver_Bearman/olibea01.png",
    ),
    BOTTAS(
        "bottas",
        "발테리 보타스",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/V/VALBOT01_Valtteri_Bottas/valbot01.png",
    ),
    COLAPINTO(
        "colapinto",
        "프랑코 콜라핀토",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/F/FRACOL01_Franco_Colapinto/fracol01.png",
    ),
    GASLY(
        "gasly",
        "피에르 가슬리",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/P/PIEGAS01_Pierre_Gasly/piegas01.png",
    ),
    HAMILTON(
        "hamilton",
        "루이스 해밀턴",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LEWHAM01_Lewis_Hamilton/lewham01.png",
    ),
    HULKENBERG(
        "hulkenberg",
        "니코 휠켄베르크",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/N/NICHUL01_Nico_Hulkenberg/nichul01.png",
    ),
    LAWSON(
        "lawson",
        "리암 로슨",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LIALAW01_Liam_Lawson/lialaw01.png",
    ),
    LECLERC(
        "leclerc",
        "샤를 르클레르",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/C/CHALEC01_Charles_Leclerc/chalec01.png",
    ),
    KEVIN_MAGNUSSEN(
        "kevin_magnussen",
        "케빈 마그누센",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/K/KEVMAG01_Kevin_Magnussen/kevmag01.png",
    ),
    NORRIS(
        "norris",
        "란도 노리스",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LANNOR01_Lando_Norris/lannor01.png",
    ),
    OCON(
        "ocon",
        "에스테반 오콘",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/E/ESTOCO01_Esteban_Ocon/estoco01.png",
    ),
    PEREZ(
        "perez",
        "세르히오 페레스",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/S/SERPER01_Sergio_Perez/serper01.png",
    ),
    PIASTRI(
        "piastri",
        "오스카 피아스트리",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/O/OSCPIA01_Oscar_Piastri/oscpia01.png",
    ),
    RICCIARDO(
        "ricciardo",
        "다니엘 리카도",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/D/DANRIC01_Daniel_Ricciardo/danric01.png",
    ),
    RUSSELL(
        "russell",
        "조지 러셀",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/G/GEORUS01_George_Russell/georus01.png",
    ),
    SAINZ(
        "sainz",
        "카를로스 사인스",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/C/CARSAI01_Carlos_Sainz/carsai01.png",
    ),
    SARGEANT(
        "sargeant",
        "로건 사전트",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LOGSAR01_Logan_Sargeant/logsar01.png",
    ),
    STROLL(
        "stroll",
        "랜스 스트롤",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LANSTR01_Lance_Stroll/lanstr01.png",
    ),
    TSUNODA(
        "tsunoda",
        "유키 츠노다",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/Y/YUKTSU01_Yuki_Tsunoda/yuktsu01.png",
    ),
    MAX_VERSTAPPEN(
        "max_verstappen",
        "막스 베르스타펜",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/M/MAXVER01_Max_Verstappen/maxver01.png",
    ),
    ZHOU(
        "zhou",
        "저우 관위",
        "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/G/GUAZHO01_Guanyu_Zhou/guazho01.png",
    ),
    ;

    companion object {
        fun findByDriverIdOrNull(driverId: String) = entries.find { it.driverId == driverId }
    }
}
