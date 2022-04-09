import * as React from "react"
import Svg, { Defs, G, Circle, Path } from "react-native-svg"
/* SVGR has dropped some elements not supported by react-native-svg: filter */

function SvgComponent(props) {
  return (
    <Svg xmlns="http://www.w3.org/2000/svg" width={84} height={84} {...props}>
      <Defs></Defs>
      <G data-name="\uADF8\uB8F9 624">
        <G data-name="\uADF8\uB8F9 596">
          <G data-name="\uADF8\uB8F9 240">
            <G filter="url(#prefix__a)" data-name="\uADF8\uB8F9 139">
              <G
                data-name="\uD0C0\uC6D0 6"
                transform="translate(10 9)"
                fill="#153682"
                stroke="#fbfbfb"
                strokeWidth={2}
              >
                <Circle cx={30} cy={30} r={30} stroke="none" />
                <Circle cx={30} cy={30} r={29} fill="none" />
              </G>
            </G>
          </G>
        </G>
        <G
          data-name="\uADF8\uB8F9 589"
          fill="none"
          stroke="#fbfbfb"
          strokeLinecap="round"
          strokeWidth={4}
        >
          <Path data-name="\uC120 73" d="M28 39h24" />
          <Path data-name="\uC120 74" d="M28 49h24" />
          <Path data-name="\uC120 75" d="M28 29h24" />
        </G>
      </G>
    </Svg>
  )
}

export default SvgComponent
