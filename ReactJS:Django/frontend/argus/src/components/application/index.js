// react-learn JS libraries
import { MapContainer, TileLayer, Marker, Popup, useMap, LayersControl, GeoJSON, LayerGroup, Circle, CircleMarker } from 'react-leaflet'
import '../../App.css';
import React, { useEffect, useState, Component } from 'react'
import Head from '../header'
import L, { circle, icon, latLng, latLngBounds, map } from "leaflet";
import img from "./bg.jpg"
import streetMapTileIcon from "./streetMapImg.jpg"
import satelliteMapTileIcon from "./satelliteMapImg.png"
import minimalistMapIcon from "./minimalistMapImg.png"
import earthquakeIcon from "./earthquake-icon.png"
import tsunamiIcon from "./ocean-waves-icon.png"
import { LatLng } from "leaflet"
import { GeoSearchControl, OpenStreetMapProvider } from 'leaflet-geosearch';
import earthquakedatas from '../earthquake_plot/pastmonth.json'
import Chloropleth_legends from '../chloropleth_map/chloropleth_legends';
import ETlegendtest from '../earthquake_plot/etlegend';
import moment from 'moment'
import Cookies from 'universal-cookie';

window.choice = localStorage.getItem("ea") == null ? "temperature" : localStorage.getItem("ea")
window.date = moment().toISOString()
window.start_date = moment().toISOString()
window.end_date = moment().toISOString()
window.time = 0
window.checked = false

window.x = 0
window.y = 0

var markers = L.layerGroup()

var cookies = new Cookies();

function GetIcon(_iconSize){
  var icon = ''
  if(window.choice === "temperature"){
    icon = require("./icons/temperature.png")
  }else if(window.choice === "humidity"){
    icon = require("./icons/humid.png")
  }else if(window.choice === "sea level"){
    icon = require("./icons/sealevel.png")
  }else if(window.choice === "co2"){
    icon = require("./icons/co2.png")
  }else if(window.choice === "no2"){
    icon = require("./icons/no2.png")
  }else{
    icon = require("./icons/ozone.png")
  }
  return L.icon({
    iconUrl: icon,
    iconSize: _iconSize,
  })
}

function removeMarker(obj) {
  const btn = document.querySelector(".remove");
  btn.addEventListener("click", (e) => {
    markers.removeLayer(obj.sourceTarget)
  })
}

function Calendars(use_range) {
  if(use_range) {
    document.getElementById("singlemode").style.display = "none";
    document.getElementById("rangemode").style.display = "inline";
    document.getElementById("asd").style.display = "none";
  } else {
    document.getElementById("singlemode").style.display = "inline";
    document.getElementById("rangemode").style.display = "none";
    document.getElementById("asd").style.display = "inline";
  }
}

//function to search location by name
const Search = (props) => {
  const [x, setX] = useState(0);
  const [y, setY] = useState(0);
  const [lab, setLabel] = useState(null);
  const [ea, setEA] = useState(window.choice)
  const map = useMap()
  const { provider } = props

  useEffect(() => {
    Fetchdata();
  }, [x, y, ea])

  //retrieve the EA data when user searched location
  const Fetchdata = async () => {
    let name = "test"
    var response = null
    var res = null
    if (window.checked === true) {
      response = await fetch('http://127.0.0.1:8000/arg/avg_api/', {
        method: 'POST',
        body: JSON.stringify({ 'latitude': y, 'longitude': x, 'EA': window.choice, 'start_date': window.start_date, 'end_date': window.end_date }),
        headers: {
          'Accept': 'application/json, text/plain',
          'Content-Type': 'application/json; charset=utf-8'
        }
      })
    }
    else {
      response = await fetch('http://127.0.0.1:8000/arg/api/', {
        method: 'POST',
        body: JSON.stringify({ 'latitude': y, 'longitude': x, 'EA': window.choice, 'date': window.date, 'time': window.time }),
        headers: {
          'Accept': 'application/json, text/plain',
          'Content-Type': 'application/json; charset=utf-8'
        }
      })    
    }
    res = await response.json();
    
    cookies.set('EA', window.choice, { path: '/' });
    localStorage.setItem("ea", window.choice);
    console.log(cookies.get('EA'));

    if (x != 0 && y != 0) {
      var date = new Date(window.date)
      date.setHours(date.getHours() + window.time)
      var measurement = null
      if (window.choice == "temperature") {
        measurement = "°C"
      } else if (window.choice == "humidity") {
        measurement = "%"
      } else if (window.choice == "sea level") {
        measurement = "inch"
      } else {
        measurement = " (tons)"
      }
      var temp_data = JSON.stringify(res).replaceAll("{", "").replaceAll("\"", "").replaceAll("}", "").replace(":", ": ").split(',')
      var button = `<button class="remove" type="button">Remove me</button>`
      var temp_dd = date.toLocaleString() + "<br>" + "Coordinate: " + x + ", " + y + "<br>" + temp_data[1].replace(":", " (").replace(":", "): ") + measurement + "<br>"
      localStorage.setItem("data", temp_dd)
      var first = temp_dd.split(":")[1]
      L.marker([y, x],{icon: GetIcon(40)}).bindPopup(temp_dd + button).on("popupopen", removeMarker).addTo(markers)
      markers.addTo(map)
    }
  }

  
 
  //search the location by location_label
  useEffect(() => {
    const searchControl = new GeoSearchControl({
      provider,
      autoComplete: true,
      showPopup: false,
      showMarker: false,
      popupFormat: ({ query, result }) => {
        setX(result.x);
        setY(result.y);
        localStorage.setItem("x", result.x)
        localStorage.setItem("y", result.y)
        setLabel(result.label);
        return result.label;
      }
    }).addTo(map)
    return () => map.removeControl(searchControl)
  }, [props])

  return null
}

//function to retrieve user's current location
const CurrentLocation = () => {
  const [position, setPosition] = useState(null)
  const [bbox, setBbox] = useState([])
  const map = useMap()

  useEffect(() => {
      map.locate().on("locationfound", function (e) {
        setPosition(e.latlng)
        console.log(e.latlng)
        map.flyTo(e.latlng, map.getZoom());
        setBbox(e.bounds.toBBoxString().split(","))
      });
  }, [map])

  return position === null ? null : (
    <Marker position={position}>
      <Popup>
        You are here. <br />
        <b>SW lng</b>: {bbox[0]} <br />
        <b>SW lat</b>: {bbox[1]} <br />
        <b>NE lng</b>: {bbox[2]} <br />
        <b>NE lat</b>: {bbox[3]}
      </Popup>
    </Marker>
  )
}

const PreviousLocation = () => {
  const map = useMap()
  window.y = localStorage.getItem("y")
  window.x = localStorage.getItem("x")
  window.choice = localStorage.getItem("ea")
  //console.log(window.y)
  //console.log(window.x)
  console.log(window.choice)
  useEffect(() => {
      map.setView([window.y, window.x])
  }, [])
  const temp_data = localStorage.getItem("data")
  //console.log(temp_data)
  var button = `<button class="remove" type="button">Remove me</button>`
  L.marker([window.y, window.x],{icon: GetIcon(40)}).bindPopup(temp_data + button).on("popupopen", removeMarker).addTo(markers)
  markers.addTo(map)
  return null;
}

const TimeDependentComponents = (props) => {
  const [windowTime, setWindowTime] = useState(window.time)

  const handleTimeFrameChange = (updatedTimeFrameValue) => {
    setWindowTime(updatedTimeFrameValue)
  }

  return (
    <div>
      <span id="asd" style={{display: "inline"}}>
        <SliderForTimeFrame id="asd" onchangeTimeFrame={handleTimeFrameChange} timeframe={windowTime} />
      </span>
      <Choropleth timeframe={windowTime} />
    </div>
  )
}

const Choropleth = (props) => {

  const ChoroplethMap = (props) => {

    const [legendToggle, setLegendToggle] = useState(false)
    const [geoData, setGeoData] = useState(null)
    const [queryDate, setQueryDate] = useState(null)

    const fetchGeoData = async () => {
      if (props.ea_type === 'none') {
        return
      }
      setGeoData(null)
      var date = new Date(window.date)
      date.setHours(date.getHours() + props.timeframe)
      setQueryDate(date)
      date = date.toISOString().split('.')[0]
      if (window.checked === true) {
        const response = await fetch('http://127.0.0.1:8000/arg/avg_geojson/', {
          method: 'POST',
          body: JSON.stringify({ 'ea': props.ea_type, 'start_datetime':window.start_date, 'end_datetime': window.end_date }),
          headers: {
            'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json; charset=utf-8'
          }
        })
        const res = await response.json();
        setGeoData(res)
      }
      else {
        const response = await fetch('http://127.0.0.1:8000/arg/geojson/', {
          method: 'POST',
          body: JSON.stringify({ 'ea': props.ea_type, 'datetime': date }),
          headers: {
            'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json; charset=utf-8'
          }
        })
        const res = await response.json();
        setGeoData(res)
      }
      
    }

    const highlightChloropleth = (e => {
      var layer = e.target
      layer.setStyle({
        weight: 1,
        color: "black",
        fillOpacity: 1
      })
    })

    const resetHighlight = (e => {
      e.target.setStyle(chloropleth_style(e.target.feature));
    })

    const onEachFeature = (feature, layer) => {
      const name = feature.properties.ADMIN
      const value = feature.properties.value
      layer.bindPopup(`<strong>name: ${name} <br/> ${props.ea_type}: ${value} <br/> Date: ${queryDate.toLocaleString().substring(0, 24)}</strong>`)
      layer.on({
        mouseover: highlightChloropleth,
        mouseout: resetHighlight,
      })
    }

    const mapPolygonColorToDensity = (value => {
      if (props.ea_type === "temperature") {
        return value > 18
          ? '#a50f15'
          : value > 14
            ? '#de2d26'
            : value > 8
              ? '#fb6a4a'
              : value > 3
                ? '#fc9272'
                : value > -4
                  ? '#fcbba1'
                  : '#fee5d9';
      }
      else if (props.ea_type === "humidity") {
        return value > 10
          ? '#FF8300'
          : value > 5
            ? '#FE992D'
            : value > 4
              ? '#FFA84B'
              : value > 3
                ? '#FFBF7B'
                : value > 2
                  ? '#FFD7AC'
                  : '#FCE0C2';
      }
      else if (props.ea_type === "sea level") {
        return value > 10
          ? '#005D59'
          : value > 5
            ? '#00746F'
            : value > 4
              ? '#0CD1CA'
              : value > 3
                ? '#1EE1DA'
                : value > 2
                  ? '#94F3EF'
                  : '#CFFCFA';
      }
      else if (props.ea_type === "co2") {
        return value > 80
          ? '#006834'
          : value > 60
            ? '#009149'
            : value > 50
              ? '#00BE60'
              : value > 40
                ? '#00DA6F'
                : value > 30
                  ? '#A4ECC8'
                  : '#C9EEDC';
      }
      else if (props.ea_type === "no2") {
        return value > 20
          ? '#006834'
          : value > 15
            ? '#009149'
            : value > 10
              ? '#00BE60'
              : value > 5
                ? '#00DA6F'
                : value > 2
                  ? '#A4ECC8'
                  : '#C9EEDC';
      }
      else if (props.ea_type === "ozone") {
        return value > 40
          ? '#006834'
          : value > 30
            ? '#009149'
            : value > 20
              ? '#00BE60'
              : value > 10
                ? '#00DA6F'
                : value > 5
                  ? '#A4ECC8'
                  : '#C9EEDC';
      }
      else {
        return null
      }

    })

    const chloropleth_style = (feature => {
      return ({
        fillColor: mapPolygonColorToDensity(feature.properties.value),
        weight: 1,
        opacity: 1,
        color: 'white',
        dashArray: '2',
        fillOpacity: 0.5
      })
    })

    return (
      <BaseLayer name={`Choropleth map - ${props.ea_type}`} checked={props.checked}>
        <LayerGroup
          eventHandlers={
            {
              add: () => {
                fetchGeoData()
                setLegendToggle(true)
              },
              remove: () => {
                setLegendToggle(false)
              }
            }
          }
        >
          {geoData && (<GeoJSON data={geoData} onEachFeature={onEachFeature} style={chloropleth_style} />)}
        </LayerGroup>
        {legendToggle ? <Chloropleth_legends ea_type={props.ea_type} /> : null}
      </BaseLayer>
    )
  }
  return (
    <LayersControl>
      <ChoroplethMap timeframe ={props.timeframe} ea_type="temperature" checked={false} />
      <ChoroplethMap timeframe ={props.timeframe} ea_type="humidity" checked={false} />
      <ChoroplethMap timeframe ={props.timeframe} ea_type="sea level" checked={false} />
      <ChoroplethMap timeframe ={props.timeframe} ea_type="co2" checked={false} />
      <ChoroplethMap timeframe ={props.timeframe} ea_type="no2" checked={false} />
      <ChoroplethMap timeframe ={props.timeframe} ea_type="ozone" checked={false} />
      <ChoroplethMap timeframe ={props.timeframe} ea_type="none" checked={false} />
    </LayersControl>
  )
}

const SliderForTimeFrame = (props) => {
  const style = {
    position: "absolute",
    top: "20vh",
    height: "100%",
    zIndex: "999"
  }
  const map = useMap()

  var today = new Date()
  var min = today.getHours() * -1
  var max = 0
  var timeLeftInTheDay = 24 - today.getHours()
  if (timeLeftInTheDay < 4) {
    max = timeLeftInTheDay - 1
  }
  else {
    max = 4
    
  }
  max = '' + max
  return (
    <div style={style}>
      <input
        type="range"
        min={min}
        max={max}
        defaultValue={0}
        onMouseEnter={
          (e) => {
            map.dragging.disable()
          }
        }
        onMouseOut={
          (e) => {
            map.dragging.enable()
          }
        }
        onChange={
          (e) => {
            window.time = e.target.valueAsNumber
            map.removeLayer(markers)
            markers.clearLayers()
            map.addLayer(markers)
            props.onchangeTimeFrame(e.target.valueAsNumber)
          }
        }
      >
      </input>
    </div>
  )
}

const Earthquake = () => {
  const [legendToggleET, setLegendToggleET] = useState(false)
  const [ed, setED] = useState(null)

  const fetchETData = async () => {
    const response = await fetch('https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson')
    const res = await response.json();
    setED(res)
  }

  const point = (feature, layer) => {
    const tsunamicheck2 = feature.properties.tsunami
    const mag2 = feature.properties.mag

    if (tsunamicheck2 == 0) {
      return new L.CircleMarker([feature.geometry.coordinates[1], feature.geometry.coordinates[0]], { radius: mag2 * 10, color: "#f5363d", fillOpacity: 0.1 })
    }
    else {
      return new L.CircleMarker([feature.geometry.coordinates[1], feature.geometry.coordinates[0]], { radius: mag2 * 10, color: "#025fc9" })
    }
  }

  const onEachFeature = (feature, layer) => {
    const lat = feature.geometry.coordinates[1]
    const long = feature.geometry.coordinates[0]
    const tsunamicheck = feature.properties.tsunami
    const mag = feature.properties.mag
    const place = feature.properties.place
    const date = new Date(feature.properties.time)
    if (tsunamicheck == 0) {
      layer.bindPopup(`<strong> <center>Earthquake</center></strong> <br/> Date: ${date} <br/>  Magntitude: ${mag} <br/> Location: ${place} <br/> Coordinates: ${[lat, long]}`)
    }
    else {
      layer.bindPopup(`<strong> <center>Tsunami Flag</center></strong> <br/> Date: ${date} <br/> Magntitude: ${mag} <br/> Location: ${place} <br/> Coordinates: ${[lat, long]}`)
    }
  }
  return (
    <LayersControl>
      <LayersControl.Overlay name="Earthquake and Tsunami Plot">
        <LayerGroup
          eventHandlers={
            {
              add: () => {
                fetchETData()
                setLegendToggleET(true)
              },
              remove: () => {
                setLegendToggleET(false)
              }
            }
          }
        >
          {ed && (<GeoJSON data={ed} onEachFeature={onEachFeature} pointToLayer={point} />)}
        </LayerGroup>
        {legendToggleET ? <ETlegendtest /> : null}
      </LayersControl.Overlay>
    </LayersControl>
  )
}

const { BaseLayer } = LayersControl

const Application = () => {

  const mapStyle = {
    margin: '0 auto',
  }

  const handleCheckbox = () => {
    window.checked = !window.checked
    Calendars(window.checked)
  }

  useEffect(() => {
    console.log(localStorage.getItem("x"))
    if(localStorage.getItem("x") != null && localStorage.getItem("y") != null){
      window.x = localStorage.getItem("x")
      window.y = localStorage.getItem("y")
    }  
  })
  return (
    <div style={{
      backgroundImage: `url(${img})`,
      backgroundRepeat: `no-repeat`,
      height: '100vh',
      backgroundPosition: 'center',
    }}>
      <div>
        <Head />
      </div>
      <div id="map">
        <form>
          <div style={{
            marginTop: 10,
            marginBottom: 10,
            width: '100%',

          }}>
            <div>
              <select defaultValue={window.choice} style={{ width: 100 }} onChange={(event) => window.choice = event.target.value}>
                <option value="temperature">Temperature</option>
                <option value="sea level">Sea Level</option>
                <option value="humidity">Humidity</option>
                <option value="co2">CO2</option>
                <option value="no2">NO2</option>
                <option value="ozone">Ozone</option>
              </select>
              <br/>
              <label style={{color:'white'}}>&nbsp;Use Date Ranges:&nbsp;</label>
              <input type="checkbox" onChange={handleCheckbox}/>
              <br/>
              <span id="singlemode" style={{display: "inline"}}>
              <label style={{color:'white'}}>&nbsp;Date:</label>
                <input id="singlecalendar" type="date" onChange={e => window.date = new Date(e.target.value.concat('T00:00:00'))} max={moment().format('YYYY-MM-DD')} min={moment().subtract(5, 'year').format("YYYY-MM-DD")} defaultValue={window.date} />
              </span>
              <span id="rangemode" style={{display: "none"}}>
                <label style={{color:'white'}}>&nbsp;Start Date:</label>
                <input id="startcalendar" type="date" onChange={e => window.start_date = new Date(e.target.value.concat('T00:00:00'))} max={moment().format("YYYY-MM-DD")} min={moment().subtract(5, 'year').format("YYYY-MM-DD")} defaultValue={window.start_date} />
                <label style={{color:'white'}}>&nbsp;End Date:</label>
                <input id="endcalendar" type="date" onChange={e => window.end_date = new Date(e.target.value.concat('T00:00:00'))} max={moment().format("YYYY-MM-DD")} min={moment().subtract(5, 'year').format("YYYY-MM-DD")} defaultValue={window.end_date} />
              </span>
            </div>
            <div />
          </div>
        </form>
        <MapContainer center={[51.505, -0.09]} zoom={13} scrollWheelZoom={true} style={mapStyle}>
          <LayersControl>
            <BaseLayer checked name={`<img src=${streetMapTileIcon} alt="street" width=100/>`}>
              <TileLayer
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="http://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}"
                subdomains={['mt1', 'mt2', 'mt3']}
              />
            </BaseLayer>
            <BaseLayer name={`<img src=${satelliteMapTileIcon} alt="satellite" width=100/>`}>
              <TileLayer
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}"
                subdomains={['mt1', 'mt2', 'mt3']}
              />
            </BaseLayer>
            <BaseLayer name={`<img src=${minimalistMapIcon} alt="minimalist" width=100/>`}>
              <TileLayer
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="https://stamen-tiles-{s}.a.ssl.fastly.net/toner-lite/{z}/{x}/{y}{r}.{ext}"
                subdomains='abcd'
                ext='png'
              />
            </BaseLayer>
          </LayersControl>
          <TimeDependentComponents useDateRange={window.checked}/>
          <Earthquake />
          <Search provider={new OpenStreetMapProvider()} />
          {(localStorage.getItem("x") === null && localStorage.getItem("y") === null) ? <CurrentLocation /> : <PreviousLocation />}
        </MapContainer>
      </div>
    </div>
  )
}

export default Application;
