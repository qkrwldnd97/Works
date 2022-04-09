import * as React from "react"
import Svg, { Defs, G, Circle, Text, TSpan } from "react-native-svg"
/* SVGR has dropped some elements not supported by react-native-svg: filter */

function SvgComponent(props) {
  return (
    <Svg xmlns="http://www.w3.org/2000/svg" width={84} height={84} {...props}>
      <Defs></Defs>
      <G data-name="\uADF8\uB8F9 616">
        <G data-name="\uADF8\uB8F9 613">
          <G data-name="\uADF8\uB8F9 611">
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
            </G>
          </G>
        </G>
        <Text
          transform="translate(21 46)"
          fill="#aaa"
          fontSize={17}
          fontFamily="Roboto-Medium, Roboto"
          fontWeight={500}
        >
          <TSpan x={0} y={0}>
            {"Voca"}
          </TSpan>
        </Text>
      </G>
    </Svg>
  )
}

export default SvgComponent
