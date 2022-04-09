import * as React from "react"
import Svg, { G, Path } from "react-native-svg"

function SvgComponent(props) {
  return (
    <Svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width={20.035}
      height={22.494}
    >
      <G
        data-name="\uADF8\uB8F9 462"
        stroke="#aaa"
        strokeLinecap="round"
        strokeWidth={2}
      >
        <Path
          data-name="\uD328\uC2A4 160"
          d="M1.414 1.414l8.6 8.6 8.6-8.6"
          fill="#aaa"
        />
        <Path
          data-name="\uD328\uC2A4 161"
          d="M10.017 10.017v11.477"
          fill="none"
        />
      </G>
    </Svg>
  )
}

export default SvgComponent
