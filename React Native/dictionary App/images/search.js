import * as React from "react"
import Svg, { Defs, ClipPath, Path, G, Circle } from "react-native-svg"
/* SVGR has dropped some elements not supported by react-native-svg: filter */

function SvgComponent(props) {
  return (
    <Svg xmlns="http://www.w3.org/2000/svg" width={84} height={84} {...props}>
      <Defs>
        <ClipPath id="prefix__b">
          <Path
            data-name="\uC0AC\uAC01\uD615 8653"
            fill="none"
            d="M0 0h34.551v33.519H0z"
          />
        </ClipPath>
      </Defs>
      <G data-name="\uADF8\uB8F9 621">
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
        <G data-name="\uADF8\uB8F9 620">
          <G
            data-name="\uADF8\uB8F9 619"
            clipPath="url(#prefix__b)"
            fill="#aaa"
            transform="translate(23 23)"
          >
            <Path
              data-name="\uD328\uC2A4 168"
              d="M11.89 23.785a11.893 11.893 0 118.633-3.7 11.856 11.856 0 01-8.633 3.7m.01-21.534A9.641 9.641 0 1018.534 4.9 9.62 9.62 0 0011.9 2.251"
            />
            <Path
              data-name="\uD328\uC2A4 169"
              d="M4.912 16.066a1.126 1.126 0 01-1.033-.676q-.063-.144-.121-.292a8.771 8.771 0 013.358-10.53 1.126 1.126 0 011.229 1.888 6.51 6.51 0 00-2.491 7.815l.09.219a1.127 1.127 0 01-1.032 1.577"
            />
            <Path
              data-name="\uD328\uC2A4 170"
              d="M9.207 20.149a1.128 1.128 0 01-.4-.072 8.7 8.7 0 01-2.045-1.1 1.127 1.127 0 011.323-1.824 6.425 6.425 0 001.515.814 1.126 1.126 0 01-.4 2.181"
            />
            <Path
              data-name="\uC0AC\uAC01\uD615 8652"
              d="M18.931 20.126l1.55-1.633 5.477 5.197-1.55 1.633z"
            />
            <Path
              data-name="\uD328\uC2A4 171"
              d="M34.119 32.226l-.762.8a1.574 1.574 0 01-2.225.058l-8.893-8.441a1.574 1.574 0 01-.058-2.225l.762-.8a1.574 1.574 0 012.226-.058l8.893 8.441a1.574 1.574 0 01.058 2.226"
            />
          </G>
        </G>
      </G>
    </Svg>
  )
}

export default SvgComponent
