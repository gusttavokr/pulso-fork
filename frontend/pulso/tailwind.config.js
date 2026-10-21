/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}", 
  ],
  safelist: [
    "border-blue-600",
    "border-2",
    "border-transparent",
    "scale-105",
    "shadow-lg",
    "border-[#21C694]"
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
      },
    },
  },
  plugins: [
    require('tailwind-scrollbar'),
  ],
};