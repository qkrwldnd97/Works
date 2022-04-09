import * as React from "react"
import Svg, { Defs, G, Circle, Rect, Path } from "react-native-svg"
/* SVGR has dropped some elements not supported by react-native-svg: filter */

function SvgComponent(props) {
  return (
    <Svg xmlns="http://www.w3.org/2000/svg" width={84} height={84} {...props}>
      <Defs></Defs>
      <G data-name="\uADF8\uB8F9 608">
        <G data-name="\uADF8\uB8F9 586">
          <G filter="url(#prefix__a)" data-name="\uADF8\uB8F9 139">
            <G
              data-name="\uD0C0\uC6D0 6"
              transform="translate(10 9)"
              fill="#fbfbfb"
              stroke="#aaa"
              strokeWidth={2}
            >
              <Circle cx={30} cy={30} r={30} stroke="none" />
              <Circle cx={30} cy={30} r={29} fill="none" />
            </G>
          </G>
        </G>
        <G data-name="\uADF8\uB8F9 607">
          <G data-name="\uADF8\uB8F9 603">
            <G
              data-name="\uADF8\uB8F9 606"
              stroke="#aaa"
              strokeLinecap="round"
              strokeWidth={2}
            >
              <G
                data-name="\uC0AC\uAC01\uD615 8648"
                transform="translate(27 23)"
                fill="#fff"
              >
                <Rect width={26} height={32} rx={4} stroke="none" />
                <Rect x={1} y={1} width={24} height={30} rx={3} fill="none" />
              </G>
              <G data-name="\uADF8\uB8F9 604" fill="none">
                <Path data-name="\uC120 79" d="M33.119 30.362h13.762" />
                <Path data-name="\uC120 80" d="M33.119 35.362h13.762" />
                <Path data-name="\uC120 82" d="M33.119 40.362h13.762" />
                <Path data-name="\uC120 83" d="M33.119 45.362H42.5" />
              </G>
            </G>
          </G>
        </G>
      </G>
    </Svg>
  )
}

export default SvgComponent
