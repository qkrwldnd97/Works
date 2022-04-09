import * as React from "react"
import Svg, { Defs, G, Circle, Path } from "react-native-svg"
/* SVGR has dropped some elements not supported by react-native-svg: filter */

function SvgComponent(props) {
  return (
    <Svg {...props} xmlns="http://www.w3.org/2000/svg" width={84} height={84}>
      <Defs></Defs>
      <G data-name="\uADF8\uB8F9 303">
        <G filter="url(#prefix__a)" data-name="\uADF8\uB8F9 139">
          <G
            data-name="\uD0C0\uC6D0 6"
            transform="translate(10 9)"
            fill="#fff"
            stroke="#aaa"
            strokeWidth={2}
          >
            <Circle cx={30} cy={30} r={30} stroke="none" />
            <Circle cx={30} cy={30} r={29} fill="none" />
          </G>
        </G>
        <G data-name="icon/action/search_24px">
          <Path fill="none" d="M23 22.001h33v33H23z" />
          <Path
            data-name="\u21B3Color"
            d="M54.19 56.001l-9.434-9.413v-1.492l-.509-.528a12.281 12.281 0 111.321-1.321l.528.509h1.491L57 53.191l-2.809 2.81zM36.264 26.774a8.491 8.491 0 108.491 8.491 8.5 8.5 0 00-8.491-8.491z"
            fill="#aaa"
          />
        </G>
      </G>
    </Svg>
  )
}

export default SvgComponent
