import * as React from "react"
import Svg, { Defs, G, Circle, Rect, Path, Text, TSpan } from "react-native-svg"
/* SVGR has dropped some elements not supported by react-native-svg: filter */

function SvgComponent(props) {
  return (
    <Svg xmlns="http://www.w3.org/2000/svg" width={84} height={84} {...props}>
      <Defs></Defs>
      <G data-name="\uADF8\uB8F9 623">
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
        <G data-name="\uADF8\uB8F9 593">
          <G
            data-name="\uADF8\uB8F9 592"
            fill="#fff"
            stroke="#aaa"
            strokeWidth={2}
          >
            <G data-name="\uC0AC\uAC01\uD615 8649" transform="translate(32 24)">
              <Rect width={24} height={32} rx={4} stroke="none" />
              <Rect x={1} y={1} width={22} height={30} rx={3} fill="none" />
            </G>
            <G data-name="\uC0AC\uAC01\uD615 8650" transform="translate(25 24)">
              <Rect width={24} height={32} rx={4} stroke="none" />
              <Rect x={1} y={1} width={22} height={30} rx={3} fill="none" />
            </G>
          </G>
          <Path
            data-name="\uC120 76"
            fill="none"
            stroke="#aaa"
            strokeLinecap="round"
            d="M50.5 27v26"
          />
          <Path
            data-name="\uC120 77"
            fill="none"
            stroke="#aaa"
            strokeLinecap="round"
            d="M52.5 27v26"
          />
          <Text
            transform="translate(30 29)"
            fill="#aaa"
            fontSize={6}
            fontFamily="Roboto-Medium, Roboto"
            fontWeight={500}
          >
            <TSpan x={0} y={6}>
              {"Book"}
            </TSpan>
          </Text>
        </G>
      </G>
    </Svg>
  )
}

export default SvgComponent
