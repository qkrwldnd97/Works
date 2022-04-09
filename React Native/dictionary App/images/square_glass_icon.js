import * as React from "react"
import Svg, { G, Circle, Path } from "react-native-svg"

function SvgComponent(props) {
  return (
    <Svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width={20.58}
      height={20.582}
    >
      <G data-name="\uADF8\uB8F9 531" stroke="#aaa" strokeWidth={2}>
        <G data-name="\uD0C0\uC6D0 9" fill="rgba(255,255,255,0)">
          <Circle cx={7.5} cy={7.5} r={7.5} stroke="none" />
          <Circle cx={7.5} cy={7.5} r={6.5} fill="none" />
        </G>
        <Path
          data-name="\uD328\uC2A4 167"
          d="M12.25 12.25l6.916 6.916"
          fill="none"
          strokeLinecap="round"
        />
      </G>
    </Svg>
  )
}

export default SvgComponent
