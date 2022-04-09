import * as React from "react"
import Svg, { Defs, G, Circle, Rect, Text, TSpan } from "react-native-svg"
/* SVGR has dropped some elements not supported by react-native-svg: filter */

function SvgComponent(props) {
  return (
    <Svg xmlns="http://www.w3.org/2000/svg" width={84} height={84} {...props}>
      <Defs></Defs>
      <G data-name="\uADF8\uB8F9 622">
        <G data-name="\uADF8\uB8F9 587">
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
        <G data-name="\uADF8\uB8F9 591">
          <G
            data-name="\uC0AC\uAC01\uD615 8647"
            transform="translate(31 21)"
            fill="#fff"
            stroke="#aaa"
            strokeLinecap="round"
            strokeWidth={2}
          >
            <Rect width={23} height={31} rx={4} stroke="none" />
            <Rect x={1} y={1} width={21} height={29} rx={3} fill="none" />
          </G>
          <G data-name="\uADF8\uB8F9 590">
            <G
              data-name="\uC0AC\uAC01\uD615 8648"
              transform="translate(26 26)"
              fill="#fff"
              stroke="#aaa"
              strokeLinecap="round"
              strokeWidth={2}
            >
              <Rect width={23} height={31} rx={4} stroke="none" />
              <Rect x={1} y={1} width={21} height={29} rx={3} fill="none" />
            </G>
            <Text
              transform="translate(30 32)"
              fill="#aaa"
              fontSize={10}
              fontFamily="Roboto-Medium, Roboto"
              fontWeight={500}
            >
              <TSpan x={0} y={10}>
                {"A-z"}
              </TSpan>
            </Text>
          </G>
        </G>
      </G>
    </Svg>
  )
}

export default SvgComponent
